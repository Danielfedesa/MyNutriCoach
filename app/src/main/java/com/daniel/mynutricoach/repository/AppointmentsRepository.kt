package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppointmentsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getAppointments(): List<Appointment> {
        val userId = auth.currentUser?.uid ?: return emptyList()

        return db.collection("appointments")
            .whereEqualTo("clienteId", userId)
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Appointment::class.java) }
    }

    suspend fun getUserName(): String? {
        val userId = auth.currentUser?.uid ?: return null
        return db.collection("users").document(userId).get().await().getString("nombre")
    }
}