package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppointmentsRepository (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun getAppointments(): List<Appointment> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            val querySnapshot = db.collection("users").document(userId)
                .collection("appointments")
                .orderBy("timestamp")
                .get().await()

            querySnapshot.documents.mapNotNull { doc ->
                val firestoreTimestamp = doc.getTimestamp("timestamp") // Obtener Timestamp de Firestore
                val estadoString = doc.getString("estado") ?: "Programada"

                doc.toObject(Appointment::class.java)?.copy(
                    timestamp = firestoreTimestamp ?: Timestamp.now(),
                    estado = AppointmentState.valueOf(estadoString) // Convertir String a Enum
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

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