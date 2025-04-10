package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.api.NutritionixRequest
import com.daniel.mynutricoach.api.RetrofitInstance
import com.daniel.mynutricoach.models.FoodInfo

class NutritionixRepository {

    suspend fun getNutritionData(alimentos: List<String>): List<FoodInfo> {
        val query = alimentos.joinToString(", ")
        val response = RetrofitInstance.api.getFoodInfo(NutritionixRequest(query))

        return if (response.isSuccessful && response.body() != null) {
            response.body()!!.foods.map {
                FoodInfo(
                    name = it.food_name,
                    calories = it.nf_calories.toInt(),
                    protein = it.nf_protein,
                    carbs = it.nf_total_carbohydrate,
                    fat = it.nf_total_fat
                )
            }
        } else {
            emptyList()
        }
    }
}