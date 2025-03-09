package com.daniel.mynutricoach.models

import com.google.firebase.Timestamp

data class Appointment (
    val id: String = "",
    val fecha: String = "",
    val hora: String = "",
    val estado: AppointmentState = AppointmentState.Programada,
    val timestamp: Timestamp = Timestamp.now()
)

enum class AppointmentState {
    Programada,
    Cancelada,
    Finalizada
}