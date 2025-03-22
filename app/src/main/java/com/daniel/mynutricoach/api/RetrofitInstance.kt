package com.daniel.mynutricoach.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: NutritionixApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://trackapi.nutritionix.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionixApiService::class.java)
    }
}