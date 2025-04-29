package com.daniel.mynutricoach.models

/**
 * Representa la información nutricional básica de un alimento.
 *
 * Esta clase se utiliza para almacenar los datos devueltos por la API de Nutritionix,
 * asociando cada alimento a sus valores principales de macronutrientes y calorías.
 *
 * @property name Nombre del alimento.
 * @property calories Cantidad de calorías del alimento (normalmente por 100g).
 * @property protein Cantidad de proteína en gramos.
 * @property carbs Cantidad de carbohidratos en gramos.
 * @property fat Cantidad de grasas en gramos.
 */
data class FoodInfo(
    val name: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)
