package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NutriAppointmentsRepository (
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    ) {
    suspend fun getAllAppointments(): List<Appointment> {
        return try {
            db.collectionGroup("appointments") // Buscar en todas las subcolecciones appointments
                .get()
                .await()
                .documents.mapNotNull { it.toObject(Appointment::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        try {
            val query = db.collectionGroup("appointments")
                .whereEqualTo("id", appointmentId)
                .get()
                .await()

            if (query.documents.isNotEmpty()) {
                val doc = query.documents.first()
                doc.reference.update("estado", newState)
            }
        } catch (e: Exception) {
        }
    }

    suspend fun addAppointment(clienteId: String, userName: String, fecha: String, hora: String) {
        try {
            val newAppointment = Appointment(
                id = db.collection("users").document(clienteId).collection("appointments").document().id,
                fecha = fecha,
                hora = hora,
                estado = AppointmentState.Programada,
                userName = userName
            )

            db.collection("users")
                .document(clienteId)
                .collection("appointments")
                .document(newAppointment.id)
                .set(newAppointment)
                .await()
        } catch (e: Exception) {
            // Error
        }
    }
}
