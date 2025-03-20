package com.daniel.mynutricoach.models

data class Meal(
    val tipo: String = "", // Desayuno, comida, merienda y cena
    val alimentos: List<String> = emptyList() // Lista de alimentos en esta comida
)
