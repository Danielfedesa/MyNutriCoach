package com.daniel.mynutricoach.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * ViewModel responsable de manejar los datos personales del usuario en la pantalla de perfil.
 *
 * Este ViewModel recupera desde el [UserRepository] datos como el nombre, peso actual,
 * peso objetivo, fecha de nacimiento y calcula la edad.
 *
 * @property userName Nombre completo del usuario.
 * @property userWeight Último peso registrado.
 * @property userObjective Peso objetivo definido por el usuario.
 * @property userBornDate Fecha de nacimiento del usuario (formato dd/MM/yyyy).
 * @property userAge Edad calculada a partir de la fecha de nacimiento.
 */
@RequiresApi(Build.VERSION_CODES.O)
class ProfileViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    /**
     * Nombre completo del usuario.
     * Se inicializa como un flujo mutable que se actualizará con el nombre real.
     */
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    /**
     * Último peso registrado del usuario.
     * Se inicializa como un flujo mutable que se actualizará con el peso real.
     */
    private val _userWeight = MutableStateFlow(0f)
    val userWeight: StateFlow<Float> = _userWeight

    /**
     * Peso objetivo definido por el usuario.
     * Se inicializa como un flujo mutable que se actualizará con el objetivo real.
     */
    private val _userObjective = MutableStateFlow("")
    val userObjective: StateFlow<String> = _userObjective

    /**
     * Fecha de nacimiento del usuario en formato dd/MM/yyyy.
     * Se inicializa como un flujo mutable que se actualizará con la fecha real.
     */
    private val _userBornDate = MutableStateFlow("")
    val userBornDate: StateFlow<String> = _userBornDate

    /**
     * Edad del usuario calculada a partir de la fecha de nacimiento.
     * Se inicializa como un flujo mutable que se actualizará con la edad real.
     */
    private val _userAge = MutableStateFlow(0)
    val userAge: StateFlow<Int> = _userAge

    init {
        fetchUserName()
        fetchUserWeight()
        fetchUserObjective()
        fetchUserBornDate()
    }

    /**
     * Recupera el nombre del usuario desde Firebase y lo actualiza en [_userName].
     */
    private fun fetchUserName() {
        viewModelScope.launch {
            _userName.value = repository.getUserName() ?: "Usuario"
        }
    }

    /**
     * Recupera el último peso registrado del usuario y lo almacena en [_userWeight].
     */
    private fun fetchUserWeight() {
        viewModelScope.launch {
            _userWeight.value = repository.getLatestWeight() ?: 0f
        }
    }

    /**
     * Recupera el peso objetivo del usuario y lo almacena en [_userObjective].
     */
    private fun fetchUserObjective() {
        viewModelScope.launch {
            _userObjective.value = repository.getUserObjective() ?: "--"
        }
    }

    /**
     * Recupera la fecha de nacimiento del usuario y calcula la edad.
     * Actualiza [_userBornDate] y [_userAge].
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchUserBornDate() {
        viewModelScope.launch {
            val date = repository.getUserBornDate()
            _userBornDate.value = date ?: ""
            _userAge.value = calculateAge(date ?: "")
        }
    }

    /**
     * Calcula la edad a partir de una fecha en formato dd/MM/yyyy.
     *
     * @param dateString La fecha de nacimiento como string.
     * @return La edad en años.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(dateString: String): Int {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val birthDate = LocalDate.parse(dateString, formatter)
            Period.between(birthDate, LocalDate.now()).years
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Cierra la sesión del usuario y lo redirige a la pantalla de login.
     *
     * @param navController El controlador de navegación para redirigir.
     */
    fun logout(navController: NavHostController) {
        repository.logout()
        navController.navigate("Login") {
            popUpTo("Profile") { inclusive = true }
        }
    }
}