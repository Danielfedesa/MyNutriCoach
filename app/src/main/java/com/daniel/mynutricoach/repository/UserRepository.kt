package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Progress
import com.daniel.mynutricoach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

/**
 * Repositorio responsable de gestionar operaciones relacionadas con los usuarios de la app,
 * tanto clientes como nutricionistas.
 *
 * Incluye funcionalidades para:
 * - Autenticación (registro, login, recuperación).
 * - Perfil de usuario (datos personales y objetivos).
 * - Gestión de clientes para nutricionistas.
 *
 * @property auth Instancia de [FirebaseAuth] para la autenticación de usuarios.
 * @property db Instancia de [FirebaseFirestore] para acceder a la base de datos.
 */
class UserRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // ---------------------------------------------------------
    // Autenticación y sesión
    // ---------------------------------------------------------

    /**
     * Registra un nuevo usuario en Firebase Auth y guarda su información en Firestore.
     *
     * @param email Correo electrónico.
     * @param password Contraseña.
     * @param role Rol del usuario ("cliente" por defecto).
     * @return Resultado con el ID del usuario registrado o error.
     */
    suspend fun registerUser(
        email: String,
        password: String,
        role: String = "cliente"
    ): Result<String> = runCatching {
        val userId = auth.createUserWithEmailAndPassword(email, password).await().user?.uid
            ?: throw Exception("No se pudo obtener el ID del usuario")

        val user = User(userId = userId, email = email, role = role)
        db.collection("users").document(userId).set(user).await()

        userId
    }

    /**
     * Inicia sesión con email y contraseña.
     *
     * @return Resultado indicando éxito o fallo.
     */
    suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Envía un correo de recuperación de contraseña.
     */
    suspend fun resetPassword(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email).await()
    }

    /**
     * Devuelve el usuario actualmente autenticado.
     */
    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    /**
     * Cierra la sesión del usuario.
     */
    fun logout() {
        auth.signOut()
    }

    // ---------------------------------------------------------
    // Perfil de usuario autenticado
    // ---------------------------------------------------------

    /**
     * Recupera todos los datos del usuario autenticado.
     */
    suspend fun getUserData(): User? = auth.currentUser?.uid?.let { userId ->
        db.collection("users").document(userId).get().await().toObject(User::class.java)
    }

    /**
     * Obtiene el rol del usuario autenticado.
     */
    suspend fun getUserRole(): String = auth.currentUser?.uid?.let { userId ->
        db.collection("users").document(userId).get().await().getString("role") ?: "cliente"
    } ?: "cliente"

    /**
     * Guarda o actualiza los datos del usuario.
     */
    suspend fun saveUserData(user: User): Result<Void?> = runCatching {
        db.collection("users").document(user.userId).set(user).await()
    }

    /**
     * Devuelve el nombre del usuario autenticado.
     */
    suspend fun getUserName(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
    }.getOrNull()

    /**
     * Devuelve el último peso registrado del usuario autenticado.
     */
    suspend fun getLatestWeight(): Float? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users")
                .document(userId)
                .collection("progress")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()
                .documents.firstOrNull()
                ?.getDouble("peso")
                ?.toFloat()
        }
    }.getOrNull()

    /**
     * Obtiene el peso objetivo del usuario autenticado.
     */
    suspend fun getUserObjective(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await()
                .getDouble("pesoObjetivo")?.toString()
        }
    }.getOrNull()

    /**
     * Obtiene la fecha de nacimiento del usuario autenticado.
     */
    suspend fun getUserBornDate(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("fechaNacimiento")
        }
    }.getOrNull()

    // ---------------------------------------------------------
    // Funciones para el nutricionista
    // ---------------------------------------------------------

    /**
     * Obtiene una lista de todos los usuarios con rol "cliente".
     */
    suspend fun getClientes(): List<User> = runCatching {
        db.collection("users")
            .whereEqualTo("role", "cliente")
            .get()
            .await()
            .documents.mapNotNull { it.toObject(User::class.java) }
    }.getOrDefault(emptyList())

    /**
     * Obtiene los datos de un cliente por su ID.
     */
    suspend fun getClienteById(clienteId: String): User? = runCatching {
        db.collection("users")
            .document(clienteId)
            .get()
            .await()
            .toObject(User::class.java)
    }.getOrNull()
}
