package com.daniel.mynutricoach.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DietsRepository(
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
}