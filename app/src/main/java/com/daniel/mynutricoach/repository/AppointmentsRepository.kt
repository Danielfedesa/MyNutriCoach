package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de gestionar todas las operaciones relacionadas con las citas (appointments),
 * tanto para clientes como para nutricionistas.
 *
 * Funcionalidades principales:
 * - Consultar citas del cliente autenticado.
 * - Consultar citas programadas del nutricionista autenticado.
 * - Actualizar el estado de citas.
 * - Crear nuevas citas.
 *
 * @property auth Instancia de [FirebaseAuth] utilizada para identificar el usuario autenticado.
 * @property db Instancia de [FirebaseFirestore] utilizada para interactuar con los datos almacenados.
 */
class AppointmentsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // ---------------------------------------------------------
    // Funciones para cliente
    // ---------------------------------------------------------

    /**
     * Recupera todas las citas asociadas al cliente actualmente autenticado.
     *
     * @return Lista de objetos [Appointment] correspondientes al cliente, o una lista vacía si no hay datos.
     */
    suspend fun getAppointments(): List<Appointment> {
        val userId = auth.currentUser?.uid ?: return emptyList()

        return db.collection("appointments")
            .whereEqualTo("clienteId", userId)
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Appointment::class.java) }
    }

    // ---------------------------------------------------------
    // Funciones para nutricionista
    // ---------------------------------------------------------

    /**
     * Recupera todas las citas programadas del nutricionista actualmente autenticado.
     *
     * Solo se incluyen las citas cuyo estado es [AppointmentState.Programada].
     *
     * @return Lista de objetos [Appointment] filtrados por estado programado.
     */
    suspend fun getAllAppointments(): List<Appointment> = runCatching {
        val nutricionistaId = auth.currentUser?.uid ?: return emptyList()

        db.collection("appointments")
            .whereEqualTo("nutricionistaId", nutricionistaId)
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Appointment::class.java) }
            .filter { it.estado.name == "Programada" }
    }.getOrDefault(emptyList())

    /**
     * Actualiza el estado de una cita existente en la base de datos.
     *
     * @param appointmentId ID único de la cita a actualizar.
     * @param newState Nuevo estado que se desea asignar ([AppointmentState]).
     */
    suspend fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        runCatching {
            db.collection("appointments")
                .document(appointmentId)
                .update("estado", newState)
                .await()
        }
    }

    /**
     * Añade una nueva cita para un cliente desde el perfil de nutricionista.
     *
     * @param clienteId ID del cliente.
     * @param clienteNombre Nombre del cliente.
     * @param clienteApellido Apellido del cliente.
     * @param fecha Fecha de la cita (formato: "dd/MM/yyyy").
     * @param hora Hora de la cita (formato: "HH:mm").
     */
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
