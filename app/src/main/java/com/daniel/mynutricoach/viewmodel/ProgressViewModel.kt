package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Progress
import com.daniel.mynutricoach.repository.ProgressRepository
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val progressRepository: ProgressRepository = ProgressRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _progressHistory = MutableStateFlow<List<Progress>>(emptyList())
    val progressHistory: StateFlow<List<Progress>> = _progressHistory

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    init {
        viewModelScope.launch {
            _progressHistory.value = progressRepository.getProgressHistory()
            _userName.value = userRepository.getUserName() ?: "Usuario"
        }
    }

    fun addProgress(
        clienteId: String,
        peso: Float,
        masaMuscular: Float,
        grasa: Float,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                val progress = Progress(
                    timestamp = System.currentTimeMillis(),
                    peso = peso,
                    masaMuscular = masaMuscular,
                    grasa = grasa
                )
                progressRepository.addProgress(clienteId, progress)
            }.onSuccess {
                onSuccess()
            }
        }
    }
}