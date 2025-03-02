package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.AuthRepository
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun checkSession(onNavigate: (String) -> Unit) {
        viewModelScope.launch {
            val role = userRepository.getUserRole()
            if (role != null) {
                _isAuthenticated.value = true
                _userRole.value = role
                onNavigate(if (role == "nutricionista") "Home" else "Progress")
            } else {
                _isAuthenticated.value = false
            }
        }
    }

    fun signIn(email: String, password: String, onNavigate: (String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            val result = authRepository.signIn(email, password)
            result.onSuccess {
                fetchUserRole(onNavigate)
            }.onFailure {
                _errorMessage.value = it.message ?: "Error desconocido"
            }
        }
    }

    private fun fetchUserRole(onNavigate: (String) -> Unit) {
        viewModelScope.launch {
            val role = userRepository.getUserRole()
            if (role != null) {
                _userRole.value = role
                _isAuthenticated.value = true
                onNavigate(if (role == "nutricionista") "Home" else "Progress")
            } else {
                _errorMessage.value = "No se encontró el usuario en Firestore"
            }
        }
    }

    fun resetPassword(email: String) {
        if (email.isEmpty()) {
            _errorMessage.value = "Por favor, ingresa tu correo"
            return
        }

        viewModelScope.launch {
            val result = authRepository.resetPassword(email)
            result.onSuccess {
                _errorMessage.value = "Se ha enviado un correo para restablecer tu contraseña"
            }.onFailure {
                _errorMessage.value = it.message ?: "Error al enviar el correo"
            }
        }
    }
}
