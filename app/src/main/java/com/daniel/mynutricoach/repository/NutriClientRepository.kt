package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.navigation.AppScreens
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.daniel.mynutricoach.models.Progress

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

    suspend fun getClienteById(clienteId: String): User? {
        return try {
            db.collection("users")
                .document(clienteId)
                .get()
                .await()
                .toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getProgresoCliente(clienteId: String): List<Progress> {
        return try {
            db.collection("users")
                .document(clienteId)
                .collection("progress")
                .orderBy("timestamp")
                .get()
                .await()
                .documents.mapNotNull { it.toObject(Progress::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}