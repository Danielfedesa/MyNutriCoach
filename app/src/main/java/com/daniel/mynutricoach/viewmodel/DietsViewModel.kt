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

/**
 * ViewModel que gestiona los datos de la dieta semanal del usuario.
 *
 * Se encarga de:
 * - Obtener el nombre del usuario.
 * - Cargar la dieta semanal desde Firebase.
 * - Exponer las comidas por día mediante `getMealsForDay`.
 *
 * @param application Contexto de la aplicación, necesario para `AndroidViewModel`.
 */
@RequiresApi(Build.VERSION_CODES.O)
class DietsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DietsRepository()

    /** Nombre del usuario actual */
    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    /** Mapa de comidas por día de la semana (ej. "Lunes" -> Lista de comidas) */
    private val _meals = MutableStateFlow<Map<String, List<Meal>>>(emptyMap())
    val meals: StateFlow<Map<String, List<Meal>>> = _meals

    init {
        loadDiet()
    }

    /**
     * Carga la dieta semanal y el nombre del usuario desde el repositorio.
     * En caso de fallo, muestra un mensaje de error y deja el mapa vacío.
     */
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

    /**
     * Devuelve un flujo con la lista de comidas para un día concreto, determinado por el offset desde hoy.
     *
     * @param offset Número de días desde hoy (0 = hoy, 1 = mañana, etc.)
     * @return [StateFlow] que contiene la lista de comidas del día especificado.
     */
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