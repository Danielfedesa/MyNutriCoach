package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NutriAppointmentsRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    suspend fun getAllAppointments(): List<Appointment> = runCatching {
        val nutricionistaId = auth.currentUser?.uid ?: return emptyList()

        db.collection("appointments")
            .whereEqualTo("nutricionistaId", nutricionistaId)
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Appointment::class.java) }
            .filter { it.estado.name == "Programada" }
    }.getOrDefault(emptyList())

    suspend fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        runCatching {
            db.collection("appointments")
                .document(appointmentId)
                .update("estado", newState)
                .await()
        }
    }

    suspend fun addAppointment(
        clienteId: String,
        clienteNombre: String,
        clienteApellido: String,
        fecha: String,
        hora: String
    ) {
        runCatching {
            val nutricionistaId = auth.currentUser?.uid ?: return

            val newAppointment = Appointment(
                id = db.collection("appointments").document().id,
                clienteId = clienteId,
                clienteNombre = clienteNombre,
                clienteApellido = clienteApellido,
                nutricionistaId = nutricionistaId,
                fecha = fecha,
                hora = hora,
                estado = AppointmentState.Programada
            )

            db.collection("appointments")
                .document(newAppointment.id!!)
                .set(newAppointment)
                .await()
        }
    }
}