package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Meal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DietsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun getUserName(): String? = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
    }.getOrNull()

    suspend fun getDietaSemana(): Map<String, List<Meal>> = runCatching {
        val userId = auth.currentUser?.uid ?: return emptyMap()
        val snapshot = db.collection("users")
            .document(userId)
            .collection("dieta_general")
            .document("comidas")
            .get()
            .await()

        val data = snapshot.data ?: return emptyMap()

        data.mapValues { (_, value) ->
            (value as? List<*>)?.mapNotNull { item ->
                (item as? Map<*, *>)?.let {
                    val tipo = it["tipo"] as? String ?: return@let null
                    val alimentos = it["alimentos"] as? List<String> ?: return@let null
                    Meal(tipo, alimentos)
                }
            } ?: emptyList()
        }
    }.getOrDefault(emptyMap())
}