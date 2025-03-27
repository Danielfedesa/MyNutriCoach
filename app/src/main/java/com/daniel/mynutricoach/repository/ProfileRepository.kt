package com.daniel.mynutricoach.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfileRepository (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Función para obtener el nombre del usuario
    suspend fun getUserName(): String? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId).get().await()
            document.getString("nombre")
        } catch (e: Exception) {
            null
        }
    }

    // Función para obtener el peso actual del usuario
    suspend fun getLatestWeight(): Float? {
        val userId = auth.currentUser?.uid ?: return null

        return try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("progress")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            snapshot.documents.firstOrNull()?.getDouble("peso")?.toFloat()
        } catch (e: Exception) {
            null
        }
    }

    // Función para obtener el OBJETIVO DE PESO del usuario
    suspend fun getUserObjetive(): String? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId).get().await()
            document.getDouble("pesoObjetivo")?.toString()
        } catch (e: Exception) {
            null
        }
    }

    // Función para obtener la fecha de nacimiento del usuario
    suspend fun getUserBornDate(): String? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId).get().await()
            document.getString("fechaNacimiento")
        } catch (e: Exception) {
            null
        }
    }

}