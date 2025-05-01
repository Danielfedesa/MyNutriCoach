package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.repository.DietsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar la dieta semanal de un cliente.
 *
 * Permite al nutricionista visualizar, modificar y guardar la dieta de un cliente en Firestore,
 * utilizando el repositorio [DietsRepository]. La dieta está organizada por día de la semana y tipo de comida.
 *
 * @property dietaSemana Mapa reactivo que contiene las comidas organizadas por día.
 */
class NutriDietViewModel(
    private val repository: DietsRepository = DietsRepository()
) : ViewModel() {

    /**
     * Mapa reactivo que contiene las comidas organizadas por día de la semana.
     * La clave es el día (por ejemplo, "Lunes") y el valor es una lista de comidas.
     */
    private val _dietaSemana = MutableStateFlow<Map<String, List<Meal>>>(emptyMap())
    val dietaSemana: StateFlow<Map<String, List<Meal>>> = _dietaSemana

    /**
     * Actualiza los alimentos de un tipo de comida para un día específico.
     *
     * Si ya existe una comida del mismo tipo, la reemplaza por la nueva.
     *
     * @param dia Día de la semana (por ejemplo, "Lunes").
     * @param tipo Tipo de comida (Desayuno, Comida, etc.).
     * @param alimentos Lista de alimentos asociados a esa comida.
     */
    fun actualizarComida(dia: String, tipo: String, alimentos: List<String>) {
        _dietaSemana.value = _dietaSemana.value.toMutableMap().apply {
            val comidasActualizadas = (this[dia] ?: emptyList())
                .filterNot { it.tipo == tipo }
                .toMutableList()
                .apply { add(Meal(tipo, alimentos)) }
            this[dia] = comidasActualizadas
        }
    }

    /**
     * Guarda la dieta actual del cliente en Firebase.
     *
     * @param clienteId ID del cliente.
     * @param onSuccess Función callback que se ejecuta si la operación finaliza con éxito.
     */
    fun guardarDieta(clienteId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.subirDieta(clienteId, _dietaSemana.value)
            onSuccess()
        }
    }

    /**
     * Carga la dieta existente de un cliente desde Firebase.
     *
     * @param clienteId ID del cliente.
     */
    fun cargarDieta(clienteId: String) {
        viewModelScope.launch {
            _dietaSemana.value = repository.obtenerDieta(clienteId)
        }
    }
}