package com.daniel.mynutricoach.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.repository.RoomDietRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomDietViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RoomDietRepository(application)

    private val _dietaLocal = MutableStateFlow<Map<String, List<Meal>>>(emptyMap())
    val dietaLocal: StateFlow<Map<String, List<Meal>>> = _dietaLocal

    fun cargarDietaLocal() {
        viewModelScope.launch {
            _dietaLocal.value = repository.loadDietaFromLocal()
        }
    }

    fun guardarDietaLocal(dieta: Map<String, List<Meal>>) {
        viewModelScope.launch {
            repository.saveDietaToLocal(dieta)
        }
    }
}