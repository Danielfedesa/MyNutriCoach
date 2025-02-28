package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Registra un usuario en Firebase Authentication y Firestore
    suspend fun registerUser(email: String, password: String, role: String): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("No se pudo obtener el id del usuario"))

            val user = User(userId, email, role)
            db.collection("users").document(userId).set(user).await()

            Result.success(userId) // Devuelve el id del usuario si se ha registrado correctamente
        } catch (e: Exception) {
            Result.failure(e) // Devuelve un error si ha habido algún problema
        }
    }
}