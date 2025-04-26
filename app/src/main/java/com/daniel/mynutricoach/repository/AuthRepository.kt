package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun registerUser(email: String, password: String, role: String = "cliente"): Result<String> = runCatching {
        val userId = auth.createUserWithEmailAndPassword(email, password).await().user?.uid
            ?: throw Exception("No se pudo obtener el ID del usuario")

        val user = User(userId = userId, email = email, role = role)
        db.collection("users").document(userId).set(user).await()

        userId
    }

    suspend fun signIn(email: String, password: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun resetPassword(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email).await()
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser
}
