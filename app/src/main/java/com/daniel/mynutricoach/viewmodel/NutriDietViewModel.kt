package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.repository.NutriDietRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutriDietViewModel (
    private val repository: NutriDietRepository = NutriDietRepository()
) : ViewModel() {

    private val _dietaSemana = MutableStateFlow<Map<String, List<Meal>>>(emptyMap())
    val dietaSemana: StateFlow<Map<String, List<Meal>>> = _dietaSemana

    fun actualizarComida(dia: String, tipo: String, alimentos: List<String>) {
        val nuevasComidas = _dietaSemana.value.toMutableMap()
        val comidasDelDia = nuevasComidas[dia]?.toMutableList() ?: mutableListOf()

        comidasDelDia.removeAll { it.tipo == tipo }
        comidasDelDia.add(Meal(tipo, alimentos))
        nuevasComidas[dia] = comidasDelDia

        _dietaSemana.value = nuevasComidas
    }

    fun guardarDieta(clienteId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.subirDieta(clienteId, _dietaSemana.value)
            onSuccess()
        }
    }

    fun cargarDieta(clienteId: String) {
        viewModelScope.launch {
            _dietaSemana.value = repository.obtenerDieta(clienteId)
        }
    }
}