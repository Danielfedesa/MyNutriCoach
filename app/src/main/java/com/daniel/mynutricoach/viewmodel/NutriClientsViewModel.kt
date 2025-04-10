package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.repository.NutriClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutriClientsViewModel (
private val repository: NutriClientRepository = NutriClientRepository()
) : ViewModel() {

    private val _clientes = MutableStateFlow<List<User>>(emptyList())
    val clientes: StateFlow<List<User>> = _clientes

    init {
        fetchClientes()
    }

    private fun fetchClientes() {
        viewModelScope.launch {
            _clientes.value = repository.getClientes()
        }
    }
}