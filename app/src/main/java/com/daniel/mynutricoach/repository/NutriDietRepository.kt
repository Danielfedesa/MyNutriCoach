package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Meal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NutriDietRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun subirDieta(clienteId: String, dieta: Map<String, List<Meal>>) {
        val dietaRef = db.collection("users")
            .document(clienteId)
            .collection("dieta_general")
            .document("comidas")

        val data = dieta.mapValues { it.value.map { comida ->
            mapOf("tipo" to comida.tipo, "alimentos" to comida.alimentos)
        }}

        dietaRef.set(data).await()
    }

    suspend fun obtenerDieta(clienteId: String): Map<String, List<Meal>> {
        return try {
            val snapshot = db.collection("users")
                .document(clienteId)
                .collection("dieta_general")
                .document("comidas")
                .get()
                .await()

            val data = snapshot.data ?: return emptyMap()

            data.mapValues { entry ->
                (entry.value as? List<*>)?.mapNotNull { item ->
                    (item as? Map<*, *>)?.let {
                        val tipo = it["tipo"] as? String ?: return@let null
                        val alimentos = it["alimentos"] as? List<String> ?: emptyList()
                        Meal(tipo, alimentos)
                    }
                } ?: emptyList()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }
}