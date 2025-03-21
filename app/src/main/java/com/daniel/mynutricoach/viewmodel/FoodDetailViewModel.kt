package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.FoodInfo
import com.daniel.mynutricoach.repository.NutritionixRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodDetailViewModel (
    private val repository: NutritionixRepository = NutritionixRepository()
) : ViewModel() {

    private val _nutrientes = MutableStateFlow<List<FoodInfo>>(emptyList())
    val nutrientes: StateFlow<List<FoodInfo>> = _nutrientes

    fun cargarNutrientes(alimentos: List<String>) {
        viewModelScope.launch {
            val resultados = alimentos.map { alimento ->
                repository.getNutrientInfo(alimento)
            }
            _nutrientes.value = resultados
        }
    }
}