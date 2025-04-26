package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InitialProfileViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _userData = MutableStateFlow(User())
    val userData: StateFlow<User> = _userData

    private val _saveState = MutableStateFlow<Result<Void?>?>(null)
    val saveState: StateFlow<Result<Void?>?> = _saveState

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            repository.getUserData()?.let {
                _userData.value = it
            }
        }
    }

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