package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.models.Progress
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NutriClientRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getClientes(): List<User> = runCatching {
        db.collection("users")
            .whereEqualTo("role", "cliente")
            .get()
            .await()
            .documents.mapNotNull { it.toObject(User::class.java) }
    }.getOrDefault(emptyList())

    suspend fun getClienteById(clienteId: String): User? = runCatching {
        db.collection("users")
            .document(clienteId)
            .get()
            .await()
            .toObject(User::class.java)
    }.getOrNull()

    suspend fun getProgresoCliente(clienteId: String): List<Progress> = runCatching {
        db.collection("users")
            .document(clienteId)
            .collection("progress")
            .orderBy("timestamp")
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Progress::class.java) }
    }.getOrDefault(emptyList())
}