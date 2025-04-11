package com.daniel.mynutricoach.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Progress
import com.daniel.mynutricoach.models.User
import com.daniel.mynutricoach.repository.NutriClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

class NutriClientDetailViewModel (
    private val repository: NutriClientRepository = NutriClientRepository()
) : ViewModel() {

    private val _cliente = MutableStateFlow<User?>(null)
    val cliente: StateFlow<User?> = _cliente

    private val _progreso = MutableStateFlow<List<Progress>>(emptyList())
    val progreso: StateFlow<List<Progress>> = _progreso


    fun getPesoActual(): Float {
        return progreso.value.lastOrNull()?.peso ?: 0f
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEdad(): Int {
        val fechaNacimiento = cliente.value?.fechaNacimiento ?: return 0
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
            val birthDate = LocalDate.parse(fechaNacimiento, formatter)
            val today = LocalDate.now()
            Period.between(birthDate, today).years
        } catch (e: Exception) {
            0
        }
    }

    fun loadCliente(clienteId: String) {
        viewModelScope.launch {
            val user = repository.getClienteById(clienteId)
            _cliente.value = user

            val progressList = repository.getProgresoCliente(clienteId)
            _progreso.value = progressList
        }
    }

}