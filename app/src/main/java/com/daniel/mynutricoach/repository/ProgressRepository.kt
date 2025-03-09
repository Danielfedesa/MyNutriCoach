package com.daniel.mynutricoach.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProgressRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getUserProgress(): Map<String, Any>? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId)
                .collection("progress").document("latest").get().await()
            document.data
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getProgressHistory(): List<Map<String, Any>> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            val querySnapshot = db.collection("users").document(userId)
                .collection("progress")
                .orderBy("timestamp") // 🔹 Ordena por fecha ASCENDENTE
                .get().await()

            querySnapshot.documents.mapNotNull { doc ->
                val timestamp = doc.getLong("timestamp") ?: return@mapNotNull null
                val peso = doc.getDouble("peso")?.toFloat() ?: return@mapNotNull null
                val masaMuscular =
                    doc.getDouble("masa_muscular")?.toFloat() ?: return@mapNotNull null
                val grasa = doc.getDouble("grasa")?.toFloat() ?: return@mapNotNull null

                mapOf(
                    "timestamp" to timestamp,
                    "peso" to peso,
                    "masa_muscular" to masaMuscular,
                    "grasa" to grasa
                )
            }
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