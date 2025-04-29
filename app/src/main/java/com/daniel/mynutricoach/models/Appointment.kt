package com.daniel.mynutricoach.models

import com.google.firebase.Timestamp

/**
 * Representa una cita (appointment) en la aplicación.
 *
 * Una cita puede ser programada por un nutricionista para un cliente e incluye información
 * como la fecha, hora, estado actual y detalles de los participantes.
 *
 * @property id ID único de la cita.
 * @property clienteId ID del cliente asociado a la cita.
 * @property clienteNombre Nombre del cliente.
 * @property clienteApellido Apellido del cliente.
 * @property nutricionistaId ID del nutricionista que creó la cita.
 * @property fecha Fecha de la cita en formato `yyyy-MM-dd`.
 * @property hora Hora de la cita en formato `HH:mm`.
 * @property estado Estado actual de la cita (programada, cancelada o finalizada).
 * @property timestamp Momento de creación del registro en Firestore.
 */
data class Appointment(
    val id: String = "",
    val clienteId: String = "",
    val clienteNombre: String = "",
    val clienteApellido: String = "",
    val nutricionistaId: String = "",
    val fecha: String = "",
    val hora: String = "",
    val estado: AppointmentState = AppointmentState.Programada,
    val timestamp: Timestamp = Timestamp.now()
)

/**
 * Enum que representa los posibles estados de una cita.
 *
 * - [Programada]: La cita está planificada pero aún no ha ocurrido.
 * - [Cancelada]: La cita ha sido anulada por el cliente o el nutricionista.
 * - [Finalizada]: La cita ya ha tenido lugar y ha concluido.
 */
enum class AppointmentState {
    Programada,
    Cancelada,
    Finalizada
}