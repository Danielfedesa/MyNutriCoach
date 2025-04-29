package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.repository.AppointmentsRepository
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentsViewModel(
    private val appointmentsRepository: AppointmentsRepository = AppointmentsRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        loadUserName()
        loadAppointments()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            _userName.value = userRepository.getUserName() ?: "Usuario"
        }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            _appointments.value = appointmentsRepository.getAppointments()
                .sortedByDescending { it.timestamp.toDate().time }
        }
    }
}
