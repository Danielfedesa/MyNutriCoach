package com.daniel.mynutricoach.models

data class DailyDiet(
    val dia: String = "", // Lunes, Martes, etc.
    val comidas: List<Meal> = emptyList()
)