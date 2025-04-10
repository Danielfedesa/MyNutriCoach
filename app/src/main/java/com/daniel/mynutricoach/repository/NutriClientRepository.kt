package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NutriClientRepository (
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    ) {
        suspend fun getClientes(): List<User> {
            return try {
                val snapshot = db.collection("users")
                    .whereEqualTo("role", "cliente") // 🔹 Filtra solo usuarios con rol cliente
                    .get()
                    .await()

                snapshot.documents.mapNotNull { it.toObject(User::class.java) }
            } catch (e: Exception) {
                emptyList()
            }
        }
}