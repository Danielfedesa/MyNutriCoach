package com.daniel.mynutricoach.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.repository.DietsRepository
import com.daniel.mynutricoach.repository.RoomDietRepository
import com.daniel.mynutricoach.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class DietsViewModel(
    application: Application,
    private val firebaseRepo: DietsRepository = DietsRepository(),
    private val roomRepo: RoomDietRepository = RoomDietRepository(application.applicationContext)
) : AndroidViewModel(application) {

    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    private val _meals = MutableStateFlow<Map<String, List<Meal>>>(emptyMap())
    val meals: StateFlow<Map<String, List<Meal>>> = _meals

    init {
        loadDietaPreferiblementeOnline()
    }

    private fun loadDietaPreferiblementeOnline() {
        viewModelScope.launch {
            if (NetworkUtils.hasInternet(getApplication())) {
                val dietaFirebase = firebaseRepo.getDietaSemana()
                _meals.value = dietaFirebase
                _userName.value = firebaseRepo.getUserName() ?: "Usuario"
                roomRepo.saveDietaToLocal(dietaFirebase)
            } else {
                _meals.value = roomRepo.loadDietaFromLocal()
                _userName.value = "Sin conexión"
            }
        }
    }

    fun getMealsForDay(offset: Int): StateFlow<List<Meal>> {
        val day = LocalDate.now().plusDays(offset.toLong())
            .dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
            .replaceFirstChar { it.uppercaseChar() }

        val flow = MutableStateFlow(_meals.value[day] ?: emptyList())

        // Actualiza automáticamente si cambia la dieta
        viewModelScope.launch {
            _meals.collect {
                flow.value = it[day] ?: emptyList()
            }
        }

        return flow
    }
}
