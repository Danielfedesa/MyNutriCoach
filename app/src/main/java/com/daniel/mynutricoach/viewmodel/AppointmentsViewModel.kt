package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.repository.AppointmentsRepository
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona las citas del usuario cliente.
 *
 * Este ViewModel se encarga de:
 * - Obtener el nombre del usuario autenticado.
 * - Cargar y exponer la lista de citas programadas, ordenadas por fecha descendente.
 *
 * @property appointmentsRepository Repositorio que accede a los datos de las citas.
 * @property userRepository Repositorio que accede a la información del usuario actual.
 */
class AppointmentsViewModel(
    private val appointmentsRepository: AppointmentsRepository = AppointmentsRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    /** Nombre del usuario actual */
    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    /** Lista de citas del usuario, ordenadas por fecha (más recientes primero) */
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        loadUserName()
        loadAppointments()
    }

    /**
     * Obtiene el nombre del usuario desde el repositorio y lo actualiza en el estado.
     */
    private fun loadUserName() {
        viewModelScope.launch {
            _userName.value = userRepository.getUserName() ?: "Usuario"
        }
    }

    /**
     * Carga las citas del usuario desde el repositorio y las ordena por fecha descendente.
     */
    private fun loadAppointments() {
        viewModelScope.launch {
            _appointments.value = appointmentsRepository.getAppointments()
                .sortedByDescending { it.timestamp.toDate().time }
        }
    }
}
