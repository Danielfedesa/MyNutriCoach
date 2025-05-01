package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar las citas de los clientes desde el rol de nutricionista.
 *
 * Permite ver, añadir y actualizar el estado de las citas programadas.
 * Se conecta con [AppointmentsRepository] para operaciones con Firestore.
 *
 * @property repository Repositorio que gestiona las operaciones de citas.
 */
class NutriAppointmentsViewModel(
    private val repository: AppointmentsRepository = AppointmentsRepository()
) : ViewModel() {

    /** Lista de citas actuales observables por la UI. */
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        refreshAppointments()
    }

    /**
     * Cambia el estado de una cita específica (por ejemplo, a "Finalizada" o "Cancelada").
     *
     * @param appointmentId ID único de la cita.
     * @param newState Nuevo estado de la cita (ver [AppointmentState]).
     */
    fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, newState)
            refreshAppointments()
        }
    }

    /**
     * Añade una nueva cita para un cliente.
     *
     * @param clienteId ID del cliente.
     * @param clienteNombre Nombre del cliente.
     * @param clienteApellido Apellido del cliente.
     * @param fecha Fecha seleccionada para la cita.
     * @param hora Hora seleccionada para la cita.
     * @param onComplete Callback que se ejecuta después de añadir correctamente la cita.
     */
    fun addAppointment(
        clienteId: String,
        clienteNombre: String,
        clienteApellido: String,
        fecha: String,
        hora: String,
        onComplete: () -> Unit
    ) {
        viewModelScope.launch {
            repository.addAppointment(clienteId, clienteNombre, clienteApellido, fecha, hora)
            refreshAppointments()
            onComplete()
        }
    }

    /**
     * Carga todas las citas desde la base de datos y las ordena por fecha descendente.
     */
    private fun refreshAppointments() {
        viewModelScope.launch {
            _appointments.value = repository.getAllAppointments()
                .sortedByDescending { it.timestamp.toDate().time }
        }
    }
}