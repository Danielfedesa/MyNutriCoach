package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getUserData(): User? = auth.currentUser?.uid?.let { userId ->
        db.collection("users").document(userId).get().await().toObject(User::class.java)
    }

    suspend fun getUserRole(): String = auth.currentUser?.uid?.let { userId ->
        db.collection("users").document(userId).get().await().getString("role") ?: "cliente"
    } ?: "cliente"

    suspend fun saveUserData(user: User): Result<Void?> = runCatching {
        db.collection("users").document(user.userId).set(user).await()
    }
}


