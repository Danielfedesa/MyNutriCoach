package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentsViewModel(private val repository: AppointmentsRepository = AppointmentsRepository()) : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    init {
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            val name = repository.getUserName()
            _userName.value = name ?: "Usuario"
        }
    }
}
