package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Registra un usuario en Firebase Authentication y lo guarda en Firestore
    suspend fun registerUser(email: String, password: String, role: String = "cliente"): Result<String> {
        return try {
            // 🔹 1. Crear usuario en Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("No se pudo obtener el ID del usuario"))

            // 🔹 2. Crear objeto User con valores predeterminados
            val user = User(
                userId = userId,
                email = email,
                role = role
            )

            // 🔹 3. Guardar usuario en Firestore
            db.collection("users").document(userId).set(user).await()

            // 🔹 4. Verificar que el documento se creó correctamente
            val documentSnapshot = db.collection("users").document(userId).get().await()
            if (!documentSnapshot.exists()) {
                return Result.failure(Exception("El documento del usuario no se creó en Firestore"))
            }

            return Result.success(userId)

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
