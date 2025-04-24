package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppointmentsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    // Obtener todas las citas del cliente logueado
    suspend fun getAppointments(): List<Appointment> {
        val userId = auth.currentUser?.uid ?: return emptyList()

        // Obtener sin ordenar desde Firebase (porque timestamp no existe)
        val result = db.collection("appointments")
            .whereEqualTo("clienteId", userId)
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Appointment::class.java) }

        // Ordenar localmente por fecha y hora
        return result.sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    // Obtener el nombre del cliente actual
    suspend fun getUserName(): String? =
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
}