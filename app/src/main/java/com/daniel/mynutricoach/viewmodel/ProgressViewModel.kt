package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Progress
import com.daniel.mynutricoach.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgressViewModel(private val repository: ProgressRepository = ProgressRepository()) : ViewModel() {

    private val _progressHistory = MutableStateFlow<List<Progress>>(emptyList())
    val progressHistory: StateFlow<List<Progress>> = _progressHistory

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    init {
        viewModelScope.launch {
            _progressHistory.value = repository.getProgressHistory()
            _userName.value = repository.getUserName()
        }
    }
}