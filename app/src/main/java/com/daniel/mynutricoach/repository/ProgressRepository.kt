package com.daniel.mynutricoach.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.daniel.mynutricoach.models.Progress

class ProgressRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getProgressHistory(): List<Progress> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            db.collection("users").document(userId)
                .collection("progress")
                .orderBy("timestamp")
                .get().await()
                .documents.mapNotNull { it.toObject(Progress::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getUserName(): String =
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        } ?: "Usuario"
}