package com.daniel.mynutricoach.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response

data class NutritionixRequest(val query: String)

data class NutritionixResponse(
    val foods: List<NutritionixFood>
)

data class NutritionixFood(
    val food_name: String,
    val nf_calories: Double,
    val nf_protein: Double,
    val nf_total_carbohydrate: Double,
    val nf_total_fat: Double
)

interface NutritionixApiService {
    @Headers(
        "x-app-id: e4aa5ae0",
        "x-app-key: 85be775cb7a129c95cb8a139bdfab459",
        "Content-Type: application/json"
    )
    @POST("v2/natural/nutrients")
    suspend fun getFoodInfo(@Body request: NutritionixRequest): Response<NutritionixResponse>
}
