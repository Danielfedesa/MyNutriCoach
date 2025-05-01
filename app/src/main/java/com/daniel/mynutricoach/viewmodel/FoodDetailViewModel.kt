package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.FoodInfo
import com.daniel.mynutricoach.api.NutritionixRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de obtener y exponer la información nutricional de una lista de alimentos.
 *
 * Se conecta con el repositorio [NutritionixRepository] para obtener datos desde la API de Nutritionix.
 *
 * @property repository Instancia del repositorio que maneja la lógica de red. Por defecto se crea uno nuevo.
 */
class FoodDetailViewModel(
    private val repository: NutritionixRepository = NutritionixRepository()
) : ViewModel() {

    /** Flujo que contiene la lista de información nutricional obtenida. */
    private val _nutrientes = MutableStateFlow<List<FoodInfo>>(emptyList())
    val nutrientes: StateFlow<List<FoodInfo>> = _nutrientes

    /**
     * Llama al repositorio para obtener los datos nutricionales de los alimentos dados.
     *
     * @param alimentos Lista de nombres de alimentos a consultar.
     */
    fun cargarNutrientes(alimentos: List<String>) {
        viewModelScope.launch {
            runCatching {
                repository.getNutritionData(alimentos)
            }.onSuccess {
                _nutrientes.value = it
            }.onFailure {
                _nutrientes.value = emptyList() // En caso de fallo, devolver lista vacía
            }
        }
    }
}
