package com.daniel.mynutricoach.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query


class ProfileRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getUserName(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
    }.getOrNull()

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

    suspend fun getUserObjective(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getDouble("pesoObjetivo")?.toString()
        }
    }.getOrNull()

    suspend fun getUserBornDate(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("fechaNacimiento")
        }
    }.getOrNull()

    fun logout() {
        auth.signOut()
    }
}