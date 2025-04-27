package com.daniel.mynutricoach.api

import com.daniel.mynutricoach.models.FoodInfo

class NutritionixRepository {

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
