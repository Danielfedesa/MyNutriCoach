package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.FoodInfo
import kotlinx.coroutines.delay

class NutritionixRepository {

    suspend fun getNutrientInfo(name: String): FoodInfo {
        // Simulación temporal (aquí irá la llamada real con Retrofit)
        delay(500) // simula carga
        return FoodInfo(
            name = name,
            calories = (50..300).random().toFloat(),
            protein = (1..30).random().toFloat(),
            carbs = (5..60).random().toFloat(),
            fat = (1..20).random().toFloat()
        )
    }
}