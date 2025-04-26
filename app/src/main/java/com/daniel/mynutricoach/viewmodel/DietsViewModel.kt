package com.daniel.mynutricoach.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.repository.DietsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class DietsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DietsRepository()

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    private val _meals = MutableStateFlow<Map<String, List<Meal>>>(emptyMap())
    val meals: StateFlow<Map<String, List<Meal>>> = _meals

    init {
        loadDiet()
    }

    private fun loadDiet() {
        viewModelScope.launch {
            runCatching {
                val dietaFirebase = repository.getDietaSemana()
                val name = repository.getUserName()

                _meals.value = dietaFirebase
                _userName.value = name ?: "Usuario"
            }.onFailure {
                _userName.value = "Error de conexión"
                _meals.value = emptyMap()
            }
        }
    }

    fun getMealsForDay(offset: Int): StateFlow<List<Meal>> {
        val day = LocalDate.now().plusDays(offset.toLong())
            .dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
            .replaceFirstChar { it.uppercaseChar() }

        val mealsForDay = MutableStateFlow(_meals.value[day] ?: emptyList())

        viewModelScope.launch {
            _meals.collect { updatedMeals ->
                mealsForDay.value = updatedMeals[day] ?: emptyList()
            }
        }

        return mealsForDay
    }
}