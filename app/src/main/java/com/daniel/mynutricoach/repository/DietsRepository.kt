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

            val comidasRaw = (doc[dia] as? List<*>)?.mapNotNull { it as? Map<*, *> } ?: return emptyList()

            println("🔥 Firestore devuelve para $dia: $comidasRaw") // Debug

            comidasRaw.map { raw ->
                val tipo = raw["tipo"] as? String ?: ""
                val alimentos = (raw["alimentos"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
                Meal(tipo, alimentos)
            }.also { meals ->
                println("✅ Meals parseadas: $meals") // Debug
            }

        } catch (e: Exception) {
            println("❌ Error obteniendo comidas: ${e.message}")
            emptyList()
        }
    }

    suspend fun getDietaSemana(): Map<String, List<Meal>> {
        val userId = auth.currentUser?.uid ?: return emptyMap()
        val snapshot = db.collection("users")
            .document(userId)
            .collection("dieta_general")
            .document("comidas")
            .get()
            .await()

        val data = snapshot.data ?: return emptyMap()

        return data.mapValues { (_, value) ->
            (value as? List<*>)?.mapNotNull { item ->
                (item as? Map<*, *>)?.let {
                    val tipo = it["tipo"] as? String ?: return@let null
                    val alimentos = it["alimentos"] as? List<String> ?: return@let null
                    Meal(tipo, alimentos)
                }
            } ?: emptyList()
        }
    }
}