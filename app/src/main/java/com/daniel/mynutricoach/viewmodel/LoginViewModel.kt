package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de la lógica de autenticación de usuarios.
 *
 * Maneja el inicio de sesión, verificación de sesión activa y recuperación de contraseña.
 * Utiliza [UserRepository] para interactuar con Firebase Authentication y Firestore.
 *
 * @property userRepository Repositorio que gestiona operaciones de usuario.
 */
class LoginViewModel(
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    /** Estado que indica si el usuario está autenticado. */
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated

    /** Rol del usuario autenticado ("nutricionista" o "cliente"). */
    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    /** Mensaje de error mostrado en pantalla. */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        checkSession() // Se ejecuta cuando el ViewModel se inicializa
    }

    /**
     * Verifica si hay una sesión activa y obtiene el rol del usuario.
     */
    private fun checkSession() {
        viewModelScope.launch {
            val user = userRepository.getCurrentUser()
            if (user != null) {
                val role = userRepository.getUserRole()
                _isAuthenticated.value = true
                _userRole.value = role
            } else {
                _isAuthenticated.value = false
            }
        }
    }

    /**
     * Realiza el inicio de sesión con correo y contraseña.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña.
     * @param onNavigate Acción de navegación que se ejecuta tras un login exitoso.
     */
    fun signIn(email: String, password: String, onNavigate: (String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            val result = userRepository.signIn(email, password)
            result.onSuccess {
                checkSession()
                onNavigate(if (_userRole.value == "nutricionista") "Home" else "Progress")
            }.onFailure {
                _errorMessage.value =
                    "Los datos introducidos son incorrectos, verifícalos e inténtalo de nuevo."
            }
        }
    }

    /**
     * Envía un correo de restablecimiento de contraseña al usuario.
     *
     * @param email Correo del usuario para enviar el enlace de recuperación.
     */
    fun resetPassword(email: String) {
        if (email.isEmpty()) {
            _errorMessage.value = "Por favor, ingresa tu correo"
            return
        }

        viewModelScope.launch {
            val result = userRepository.resetPassword(email)
            result.onSuccess {
                _errorMessage.value = "Se ha enviado un correo para restablecer tu contraseña"
            }.onFailure {
                _errorMessage.value = "El correo ingresado no está registrado"
            }
        }
    }
}
