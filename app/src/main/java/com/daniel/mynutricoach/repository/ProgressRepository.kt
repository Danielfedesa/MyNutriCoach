package com.daniel.mynutricoach.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.daniel.mynutricoach.models.Progress
import kotlinx.coroutines.tasks.await

class ProgressRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getProgressHistory(): List<Progress> = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId)
                .collection("progress")
                .orderBy("timestamp")
                .get()
                .await()
                .documents.mapNotNull { it.toObject(Progress::class.java) }
        }
    }.getOrNull() ?: emptyList()

    suspend fun getUserName(): String = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
    }.getOrNull() ?: "Usuario"

    suspend fun addProgress(clienteId: String, progress: Progress) {
        runCatching {
            db.collection("users")
                .document(clienteId)
                .collection("progress")
                .add(progress)
                .await()
        }
    }
}