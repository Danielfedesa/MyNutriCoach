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

@RequiresApi(Build.VERSION_CODES.O)
class ProfileViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _userWeight = MutableStateFlow(0f)
    val userWeight: StateFlow<Float> = _userWeight

    private val _userObjective = MutableStateFlow("")
    val userObjective: StateFlow<String> = _userObjective

    private val _userBornDate = MutableStateFlow("")
    val userBornDate: StateFlow<String> = _userBornDate

    private val _userAge = MutableStateFlow(0)
    val userAge: StateFlow<Int> = _userAge

    init {
        fetchUserName()
        fetchUserWeight()
        fetchUserObjective()
        fetchUserBornDate()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            _userName.value = repository.getUserName() ?: "Usuario"
        }
    }

    private fun fetchUserWeight() {
        viewModelScope.launch {
            _userWeight.value = repository.getLatestWeight() ?: 0f
        }
    }

    private fun fetchUserObjective() {
        viewModelScope.launch {
            _userObjective.value = repository.getUserObjective() ?: "--"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchUserBornDate() {
        viewModelScope.launch {
            val date = repository.getUserBornDate()
            _userBornDate.value = date ?: ""
            _userAge.value = calculateAge(date ?: "")
        }
    }

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

    fun logout(navController: NavHostController) {
        repository.logout()
        navController.navigate("Login") {
            popUpTo("Profile") { inclusive = true }
        }
    }
}