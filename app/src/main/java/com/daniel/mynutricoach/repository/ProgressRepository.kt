package com.daniel.mynutricoach.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.daniel.mynutricoach.models.Progress

class ProgressRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getUserProgress(): Progress? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId)
                .collection("progress").document("latest").get().await()
            document.toObject(Progress::class.java) // Convertir directamente a objeto Progres
        } catch (e: Exception) {
            null
        }
    }

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

    // Obtiene el nombre del usuario actual para mostrarlo en la pantalla de progreso
    suspend fun getUserName(): String? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId).get().await()
            document.getString("nombre")
        } catch (e: Exception) {
            null
        }
    }
}