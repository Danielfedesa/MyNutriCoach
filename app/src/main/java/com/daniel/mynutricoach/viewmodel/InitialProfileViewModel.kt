package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar los datos del perfil inicial del usuario.
 *
 * Se comunica con [UserRepository] para obtener y guardar información del usuario autenticado
 * en Firestore o en cualquier fuente de datos configurada.
 *
 * @property repository Repositorio que gestiona la persistencia de los datos del usuario.
 */
class InitialProfileViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    /** Estado con los datos actuales del usuario. */
    private val _userData = MutableStateFlow(User())
    val userData: StateFlow<User> = _userData

    /**
     * Estado que representa el resultado de guardar los datos.
     * Puede contener un `Result.success()` o `Result.failure()`.
     */
    private val _saveState = MutableStateFlow<Result<Void?>?>(null)
    val saveState: StateFlow<Result<Void?>?> = _saveState

    init {
        fetchUserData()
    }

    /**
     * Carga los datos del usuario desde el repositorio al inicializar el ViewModel.
     */
    private fun fetchUserData() {
        viewModelScope.launch {
            repository.getUserData()?.let {
                _userData.value = it
            }
        }
    }

    /**
     * Guarda los datos del perfil del usuario.
     *
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param telefono Teléfono de contacto.
     * @param fechaNacimiento Fecha de nacimiento en formato DD/MM/AAAA.
     * @param sexo Sexo del usuario.
     * @param estatura Estatura en centímetros.
     * @param pesoObjetivo Peso objetivo en kilogramos.
     */
    fun saveUserData(
        nombre: String,
        apellidos: String,
        telefono: String,
        fechaNacimiento: String,
        sexo: String,
        estatura: Int,
        pesoObjetivo: Double
    ) = viewModelScope.launch {
        _saveState.value = repository.saveUserData(
            _userData.value.copy(
                nombre = nombre,
                apellidos = apellidos,
                telefono = telefono,
                fechaNacimiento = fechaNacimiento,
                sexo = sexo,
                estatura = estatura,
                pesoObjetivo = pesoObjetivo
            )
        )
    }
}