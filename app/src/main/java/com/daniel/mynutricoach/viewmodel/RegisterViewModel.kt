package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> = _registerState

    fun register(email: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || password.isEmpty() || password != confirmPassword) {
            _registerState.value = Result.failure(Exception("Todos los campos son obligatorios y las contraseñas deben coincidir"))
            return
        }
        viewModelScope.launch { _registerState.value = repository.registerUser(email, password) }
    }
}
