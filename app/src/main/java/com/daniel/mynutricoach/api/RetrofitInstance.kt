package com.daniel.mynutricoach.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto singleton que gestiona la instancia de Retrofit para acceder a la API de Nutritionix.
 *
 * Proporciona:
 * - Configuración del cliente Retrofit.
 * - Conversión automática de JSON mediante Gson.
 * - Punto de acceso a [NutritionixApiService].
 */
object RetrofitInstance {

    /**
     * Instancia de [NutritionixApiService], creada una única vez para toda la aplicación.
     *
     * Se configura con:
     * - La URL base de Nutritionix.
     * - Un conversor de JSON a objetos de Kotlin utilizando Gson.
     */
    val api: NutritionixApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://trackapi.nutritionix.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionixApiService::class.java)
    }
}