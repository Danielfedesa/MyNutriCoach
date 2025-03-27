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

        init {
            checkSession() // Se ejecuta cuando el ViewModel se inicializa
        }

        fun checkSession() {
            viewModelScope.launch {
                val user = authRepository.getCurrentUser()
                if (user != null) {
                    val role = userRepository.getUserRole()
                    _isAuthenticated.value = true
                    _userRole.value = role
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
                checkSession()
                onNavigate(if (_userRole.value == "nutricionista") "Home" else "Progress")
            }.onFailure {
                _errorMessage.value = "Los datos introducidos son incorrectos, verifícalos e inténtalo de nuevo."
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
                _errorMessage.value = "El correo ingresado no está registrado"
            }
        }
    }
}
