package com.daniel.mynutricoach.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.repository.DietsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class DietsViewModel(private val repository: DietsRepository = DietsRepository()) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _mealsByDay = mutableMapOf<String, MutableStateFlow<List<Meal>>>()

    init {
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            val name = repository.getUserName()
            _userName.value = name ?: "Usuario"
        }
    }

    fun getMealsForDay(dayOffset: Int): StateFlow<List<Meal>> {
        val diasSemana = listOf("lunes", "martes", "miércoles", "jueves", "viernes")
        val date = LocalDate.now().plusDays(dayOffset.toLong())
        val dia = date.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale("es", "ES")).lowercase()

        // Asegurar que el día obtenido es válido (evitar fines de semana si no están en Firestore)
        if (dia !in diasSemana) {
            return MutableStateFlow(emptyList()) // Devolver lista vacía si el día no es válido
        }

        println("📅 Día solicitado: $dia") // Debug

        if (_mealsByDay.containsKey(dia)) return _mealsByDay[dia]!!

        val flow = MutableStateFlow<List<Meal>>(emptyList())
        _mealsByDay[dia] = flow

        viewModelScope.launch {
            val meals = repository.getMealsForDayName(dia)
            println("🍽️ Comidas cargadas en ViewModel para $dia: $meals") // Debug
            flow.value = meals
        }

        return flow
    }

}
