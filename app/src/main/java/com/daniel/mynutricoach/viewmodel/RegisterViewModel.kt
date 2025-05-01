package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable del proceso de registro de nuevos usuarios.
 *
 * Se comunica con [UserRepository] para registrar un nuevo usuario en Firebase Authentication.
 * Expone un estado [registerState] que representa el resultado del intento de registro, útil para mostrar feedback en la UI.
 *
 * @property registerState Estado del registro. Es un [Result] que puede contener un mensaje de éxito o un error.
 */
class RegisterViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    /**
     * Estado del registro. Contiene el resultado del intento de registro.
     * Puede ser un mensaje de éxito o un error.
     */
    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> = _registerState

    /**
     * Realiza el registro de un nuevo usuario con el correo y contraseña proporcionados.
     *
     * - Valida que los campos no estén vacíos y que las contraseñas coincidan.
     * - Si la validación falla, actualiza [registerState] con un error.
     * - Si es válido, intenta registrar el usuario mediante [UserRepository.registerUser].
     *
     * @param email Correo electrónico del nuevo usuario.
     * @param password Contraseña elegida por el usuario.
     * @param confirmPassword Confirmación de la contraseña.
     */
    fun register(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank() || password != confirmPassword) {
            _registerState.value =
                Result.failure(Exception("Todos los campos son obligatorios y las contraseñas deben coincidir"))
            return
        }
        viewModelScope.launch {
            _registerState.value = repository.registerUser(email, password)
        }
    }
}