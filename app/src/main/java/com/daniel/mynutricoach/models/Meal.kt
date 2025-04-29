package com.daniel.mynutricoach.models

/**
 * Representa una comida dentro de una dieta diaria.
 *
 * Cada comida tiene un tipo (por ejemplo, desayuno, almuerzo, comida, merienda o cena) y una lista
 * de alimentos que la componen.
 *
 * @property tipo Tipo de comida (e.g., "Desayuno", "Comida", "Merienda", "Cena").
 * @property alimentos Lista de nombres de los alimentos incluidos en esta comida.
 */
data class Meal(
    val tipo: String = "",
    val alimentos: List<String> = emptyList()
)
