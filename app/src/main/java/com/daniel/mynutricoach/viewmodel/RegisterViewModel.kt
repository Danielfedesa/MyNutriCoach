package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel (private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> = _registerState

    // Registra un usuario en Firebase Authentication y Firestore
    fun register(email:String, password:String, confirmPassword:String, role:String) {
        if (email.isEmpty() || password.isEmpty()) {
            _registerState.value = Result.failure(Exception("Todos los campos son obligatorios"))
            return
        }

        if  (password != confirmPassword) {
            _registerState.value = Result.failure(Exception("Las contraseñas no coinciden"))
            return
        }

        viewModelScope.launch {
            val result = repository.registerUser(email, password, role)
            _registerState.value = result
        }
    }
}
