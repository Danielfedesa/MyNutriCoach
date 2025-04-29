package com.daniel.mynutricoach.api

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Response

/**
 * Representa la estructura de la petición que se envía a la API de Nutritionix.
 *
 * @property query Consulta natural (por ejemplo: "2 huevos, 1 manzana") que describe los alimentos.
 */
data class NutritionixRequest(val query: String)

/**
 * Representa la estructura de la respuesta recibida desde la API de Nutritionix.
 *
 * @property foods Lista de alimentos analizados en la respuesta.
 */
data class NutritionixResponse(
    val foods: List<NutritionixFood>
)

/**
 * Contiene la información nutricional básica de un alimento recibido desde Nutritionix.
 *
 * @property food_name Nombre del alimento.
 * @property nf_calories Cantidad de calorías.
 * @property nf_protein Cantidad de proteínas (gramos).
 * @property nf_total_carbohydrate Cantidad de carbohidratos (gramos).
 * @property nf_total_fat Cantidad de grasas (gramos).
 */
data class NutritionixFood(
    val food_name: String,
    val nf_calories: Double,
    val nf_protein: Double,
    val nf_total_carbohydrate: Double,
    val nf_total_fat: Double
)

/**
 * Interfaz de Retrofit que define las operaciones para comunicarse con la API de Nutritionix.
 *
 * Contiene:
 * - [getFoodInfo]: Envía una consulta de alimentos en lenguaje natural y obtiene los datos nutricionales correspondientes.
 *
 * Las peticiones requieren autenticación mediante headers `x-app-id` y `x-app-key`.
 */
interface NutritionixApiService {

    /**
     * Envía una consulta natural de alimentos a la API de Nutritionix y obtiene su información nutricional.
     *
     * @param request Objeto [NutritionixRequest] que contiene la consulta.
     * @return [Response] de tipo [NutritionixResponse] con los datos nutricionales de los alimentos solicitados.
     */
    @Headers(
        "x-app-id: e4aa5ae0",
        "x-app-key: 85be775cb7a129c95cb8a139bdfab459",
        "Content-Type: application/json"
    )
    @POST("v2/natural/nutrients")
    suspend fun getFoodInfo(@Body request: NutritionixRequest): Response<NutritionixResponse>
}
