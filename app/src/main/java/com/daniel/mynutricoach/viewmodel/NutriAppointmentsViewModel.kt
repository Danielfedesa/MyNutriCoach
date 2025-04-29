package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutriAppointmentsViewModel(
    private val repository: AppointmentsRepository = AppointmentsRepository()
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        refreshAppointments()
    }

    fun updateAppointmentStatus(appointmentId: String, newState: AppointmentState) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, newState)
            refreshAppointments()
        }
    }

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

    private fun refreshAppointments() {
        viewModelScope.launch {
            _appointments.value = repository.getAllAppointments()
                .sortedByDescending { it.timestamp.toDate().time }
        }
    }
}