package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Progress
import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.repository.ProgressRepository
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar la información detallada de un cliente
 * para su visualización por parte del nutricionista.
 *
 * Se encarga de cargar los datos personales del cliente y su historial de progreso
 * desde los repositorios correspondientes.
 *
 * @property cliente Flujo con los datos del cliente consultado.
 * @property progreso Flujo con la lista de registros de progreso del cliente.
 */
class NutriClientDetailViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val progressRepository: ProgressRepository = ProgressRepository()
) : ViewModel() {

    /**
     * Flujo que contiene los datos del cliente consultado.
     * Incluye información personal como nombre, correo electrónico, etc.
     */
    private val _cliente = MutableStateFlow<User?>(null)
    val cliente: StateFlow<User?> = _cliente

    /**
     * Flujo que contiene la lista de registros de progreso del cliente.
     * Cada registro incluye información sobre el peso, IMC y fecha de registro.
     */
    private val _progreso = MutableStateFlow<List<Progress>>(emptyList())
    val progreso: StateFlow<List<Progress>> = _progreso

    /**
     * Carga los datos del cliente y su progreso utilizando su ID.
     *
     * @param clienteId ID único del cliente en Firebase.
     */
    fun loadCliente(clienteId: String) {
        viewModelScope.launch {
            _cliente.value = userRepository.getClienteById(clienteId)
            _progreso.value = progressRepository.getProgresoCliente(clienteId)
        }
    }
}
