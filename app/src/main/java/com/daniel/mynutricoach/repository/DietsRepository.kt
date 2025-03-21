package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Meal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DietsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    // Función para obtener el nombre del usuario
    suspend fun getUserName(): String? {
        val userId = auth.currentUser?.uid ?: return null
        return try {
            val document = db.collection("users").document(userId).get().await()
            document.getString("nombre")
        } catch (e: Exception) {
            null
        }
    }

    // Función para obtener las comidas de un día
    suspend fun getMealsForDayName(dia: String): List<Meal> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            val doc = db.collection("users")
                .document(userId)
                .collection("dieta_general")
                .document("comidas")
                .get().await()

            val comidasRaw = doc[dia] as? List<Map<String, Any>> ?: return emptyList()

            println("🔥 Firestore devuelve para $dia: $comidasRaw") // Debug

            comidasRaw.map { raw ->
                val tipo = raw["tipo"] as? String ?: ""
                val alimentos = raw["alimentos"] as? List<String> ?: emptyList()
                Meal(tipo, alimentos)
            }.also { meals ->
                println("✅ Meals parseadas: $meals") // Debug
            }

        } catch (e: Exception) {
            println("❌ Error obteniendo comidas: ${e.message}")
            emptyList()
        }
    }
}