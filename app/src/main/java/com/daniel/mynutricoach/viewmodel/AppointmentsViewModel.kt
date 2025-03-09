package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentsViewModel(private val repository: AppointmentsRepository = AppointmentsRepository()) : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        fetchAppointments()
        fetchUserName()
    }

    // Función para obtener las citas del usuario
    private fun fetchAppointments() {
        viewModelScope.launch {
            val fetchedAppointments = repository.getAppointments()

            // Ordenar correctamente por timestamp convertido a Long
            _appointments.value = fetchedAppointments.sortedBy { it.timestamp.toDate().time }
        }
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            val name = repository.getUserName()
            _userName.value = name ?: "Usuario"
        }
    }
}
