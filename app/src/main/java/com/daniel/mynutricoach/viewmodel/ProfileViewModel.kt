package com.daniel.mynutricoach.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class ProfileViewModel(private val repository: ProfileRepository = ProfileRepository()) : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    private val _userWeight = MutableStateFlow<Float>(0f)
    val userWeight: StateFlow<Float> = _userWeight

    private val _userObjetive = MutableStateFlow<String>("")
    val userObjetive: StateFlow<String> = _userObjetive

    private val _userBornDate = MutableStateFlow<String>("")
    val userBornDate: StateFlow<String> = _userBornDate

    private val _userAge = MutableStateFlow<Int>(0)
    val userAge: StateFlow<Int> = _userAge

    init {
        fetchUserName()
        fetchUserWeight()
        fetchUserObjetive()
        fetchUserBornDate()
    }

    // Recuperar nombre del usuario
    private fun fetchUserName() {
        viewModelScope.launch {
            val name = repository.getUserName()
            _userName.value = name ?: "Usuario"
        }
    }

    // Recuperar peso ACTUAL del usuario
    private fun fetchUserWeight() {
        viewModelScope.launch {
            val weight = repository.getLatestWeight()
            _userWeight.value = weight ?: 0f
        }
    }

    // Función para obtener el OBJETIVO DE PESO del usuario
    private fun fetchUserObjetive() {
        viewModelScope.launch {
            val objetivo = repository.getUserObjetive()
            _userObjetive.value = objetivo ?: "--"
        }
    }

    // Función para obtener la fecha de nacimiento del usuario
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchUserBornDate() {
        viewModelScope.launch {
            val date = repository.getUserBornDate()
            _userBornDate.value = date ?: ""
            _userAge.value = calculateAge(date ?: "")
        }
    }

    // Función para calcular la edad del usuario a partir de la fecha de nacimiento
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateAge(dateString: String): Int {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val birthDate = LocalDate.parse(dateString, formatter)
            val today = LocalDate.now()
            Period.between(birthDate, today).years
        } catch (e: Exception) {
            0 // En caso de error devolvemos 0 por defecto
        }
    }

}