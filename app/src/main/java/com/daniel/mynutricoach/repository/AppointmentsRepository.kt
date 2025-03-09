package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppointmentsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Función para obtener las citas del usuario
    suspend fun getAppointments(): List<Appointment> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return db.collection("users").document(userId)
            .collection("appointments")
            .orderBy("timestamp")
            .get().await()
            .documents.mapNotNull { it.toObject(Appointment::class.java) }
    }

    // Obtiene el nombre del usuario actual para mostrarlo en la pantalla de citas
    suspend fun getUserName(): String? =
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
}