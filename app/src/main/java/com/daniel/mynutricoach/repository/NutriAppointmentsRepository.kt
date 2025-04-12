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
            val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
            val nutricionistaId = auth.currentUser?.uid ?: return emptyList()

            db.collection("appointments")
                .whereEqualTo("nutricionistaId", nutricionistaId) // Aquí filtramos
                .get()
                .await()
                .documents.mapNotNull { it.toObject(Appointment::class.java) }
                .filter { it.estado.name == "Programada" } // Solo citas programadas
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        try {
            val docRef = db.collection("appointments").document(appointmentId)
            docRef.update("estado", newState)
                .await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun addAppointment(
        clienteId: String,
        clienteNombre: String,
        clienteApellido: String,
        fecha: String,
        hora: String
    ) {
        try {
            val dbRef = db.collection("appointments").document()

            val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
            val nutricionistaId = auth.currentUser?.uid ?: ""

            val newAppointment = Appointment(
                id = dbRef.id, // el ID del documento
                clienteId = clienteId,
                clienteNombre = clienteNombre,
                clienteApellido = clienteApellido,
                nutricionistaId = nutricionistaId,
                fecha = fecha,
                hora = hora,
                estado = AppointmentState.Programada
            )

            dbRef.set(newAppointment).await()

        } catch (e: Exception) {
            // Manejo de errores
            e.printStackTrace()
        }
    }
}
