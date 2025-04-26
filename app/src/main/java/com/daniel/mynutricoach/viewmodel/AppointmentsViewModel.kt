package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentsViewModel(
    private val repository: AppointmentsRepository = AppointmentsRepository()
) : ViewModel() {

    private val _userName = MutableStateFlow<String>("Usuario")
    val userName: StateFlow<String> = _userName

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        loadUserName()
        loadAppointments()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            _userName.value = repository.getUserName() ?: "Usuario"
        }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            _appointments.value = repository.getAppointments()
                .sortedByDescending { it.timestamp.toDate().time }
        }
    }
}