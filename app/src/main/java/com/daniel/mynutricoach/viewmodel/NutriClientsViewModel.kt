package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar la lista de clientes disponibles para el nutricionista.
 *
 * Recupera todos los usuarios con rol de cliente desde Firebase usando el [UserRepository].
 * Expone un flujo de estado con la lista para que la UI la consuma de forma reactiva.
 *
 * @property clientes Lista de usuarios con rol de cliente.
 */
class NutriClientsViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    /**
     * Flujo de estado que contiene la lista de clientes.
     * Inicialmente está vacío y se carga al iniciar el ViewModel.
     */
    private val _clientes = MutableStateFlow<List<User>>(emptyList())
    val clientes: StateFlow<List<User>> = _clientes

    init {
        loadClientes()
    }

    /**
     * Carga todos los clientes desde el repositorio de usuarios.
     */
    private fun loadClientes() {
        viewModelScope.launch {
            _clientes.value = repository.getClientes()
        }
    }
}