package com.daniel.mynutricoach.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgressViewModel(private val repository: ProgressRepository = ProgressRepository()) : ViewModel() {

    private val _progressData = MutableStateFlow<Map<String, Any>?>(null)
    val progressData: StateFlow<Map<String, Any>?> = _progressData

    private val _progressHistory = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val progressHistory: StateFlow<List<Map<String, Any>>> = _progressHistory

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    init {
        fetchProgressData()
        fetchProgressHistory()
        fetchUserName()
    }

    private fun fetchProgressData() {
        viewModelScope.launch {
            _progressData.value = repository.getUserProgress()
        }
    }

    private fun fetchProgressHistory() {
        viewModelScope.launch {
            val history = repository.getProgressHistory()
            history.forEach { data ->
                Log.d("FirestoreData", "Peso: ${data["peso"]}, Músculo: ${data["masa_muscular"]}, Grasa: ${data["grasa"]}")
            }
            _progressHistory.value = history
        }
    }

    // Obtiene el nombre del usuario actual para mostrarlo en la pantalla de progreso
    private fun fetchUserName() {
        viewModelScope.launch {
            val name = repository.getUserName()
            _userName.value = name ?: "Usuario"
        }
    }

}