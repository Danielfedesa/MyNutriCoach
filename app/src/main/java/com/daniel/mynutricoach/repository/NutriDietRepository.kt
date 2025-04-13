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
}