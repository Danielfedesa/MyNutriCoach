package com.daniel.mynutricoach.api

import com.daniel.mynutricoach.models.FoodInfo

/**
 * Repositorio encargado de gestionar las consultas a la API de Nutritionix.
 *
 * Permite obtener información nutricional sobre una lista de alimentos,
 * formateando adecuadamente las cantidades para asegurar una respuesta precisa de la API.
 *
 * ### Funciones principales:
 * - Formatea cada alimento para especificar 100g si el usuario no indica una cantidad explícita.
 * - Realiza la consulta a la API y transforma la respuesta en una lista de objetos [FoodInfo].
 */
class NutritionixRepository {

    /**
     * Obtiene los datos nutricionales de una lista de alimentos desde la API de Nutritionix.
     *
     * Si un alimento no especifica peso en gramos, automáticamente se le asigna "100g de [alimento]".
     *
     * @param alimentos Lista de nombres de alimentos a consultar.
     * @return Una lista de objetos [FoodInfo] con calorías, proteínas, carbohidratos y grasas, o una lista vacía si falla.
     */
    suspend fun getNutritionData(alimentos: List<String>): List<FoodInfo> = runCatching {
        val query = alimentos.joinToString(", ") { alimento ->
            val regex = Regex("""\d+.*g""", RegexOption.IGNORE_CASE)
            if (regex.containsMatchIn(alimento)) {
                alimento
            } else {
                "100g de $alimento"
            }
        }

        val response = RetrofitInstance.api.getFoodInfo(NutritionixRequest(query))

        response.body()?.foods?.map {
            FoodInfo(
                name = it.food_name,
                calories = it.nf_calories.toInt(),
                protein = it.nf_protein,
                carbs = it.nf_total_carbohydrate,
                fat = it.nf_total_fat
            )
        } ?: emptyList()
    }.getOrDefault(emptyList())
}
