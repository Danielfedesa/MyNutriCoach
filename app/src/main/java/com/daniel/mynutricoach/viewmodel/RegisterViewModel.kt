package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> = _registerState

    fun register(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank() || password != confirmPassword) {
            _registerState.value = Result.failure(Exception("Todos los campos son obligatorios y las contraseñas deben coincidir"))
            return
        }
        viewModelScope.launch {
            _registerState.value = repository.registerUser(email, password)
        }
    }
}