package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.repository.NutriAppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutriAppointmentsViewModel(
    private val repository: NutriAppointmentsRepository = NutriAppointmentsRepository()
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        viewModelScope.launch {
            _appointments.value = repository.getAllAppointments()
                .sortedByDescending { it.timestamp.toDate().time } // Ordenar por fecha
        }
    }

    fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, newState)
            _appointments.value = repository.getAllAppointments() // Actualizar la lista de citas después de cambiar el estado
                .sortedByDescending { it.timestamp.toDate().time } // Ordenar por fecha
        }
    }

    // Método para añadir una cita
    fun addAppointment(clienteId: String, userName: String, fecha: String, hora: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.addAppointment(clienteId, userName, fecha, hora)
            _appointments.value = repository.getAllAppointments()
                .sortedByDescending { it.timestamp.toDate().time }
            onComplete()
        }
    }

}