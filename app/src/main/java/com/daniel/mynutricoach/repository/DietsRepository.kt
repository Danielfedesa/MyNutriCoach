package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Meal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de gestionar las dietas de los usuarios, tanto clientes como nutricionistas.
 *
 * Proporciona métodos para:
 * - Consultar el nombre del usuario autenticado.
 * - Obtener la dieta semanal del cliente autenticado.
 * - Guardar y consultar dietas asignadas a clientes por parte del nutricionista.
 *
 * @property auth Instancia de [FirebaseAuth] para acceder al usuario autenticado.
 * @property db Instancia de [FirebaseFirestore] para acceder a los datos en Firestore.
 */
class DietsRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // ---------------------------------------------------------
    // Funciones comunes
    // ---------------------------------------------------------

    /**
     * Obtiene el ID del usuario actualmente autenticado.
     *
     * @return El ID del usuario o `null` si no hay sesión activa.
     */
    private fun currentUserId(): String? = auth.currentUser?.uid

    /**
     * Obtiene el nombre del usuario autenticado desde Firestore.
     *
     * @return Nombre del usuario o `null` si ocurre un error o no existe.
     */
    suspend fun getUserName(): String? = runCatching {
        currentUserId()?.let { userId ->
            db.collection("users").document(userId).get().await().getString("nombre")
        }
    }.getOrNull()

    // ---------------------------------------------------------
    // Funciones para el cliente
    // ---------------------------------------------------------

    /**
     * Recupera la dieta semanal del usuario autenticado.
     *
     * Esta dieta está organizada como un mapa de días a listas de comidas ([Meal]).
     *
     * @return Mapa con la dieta del usuario, o vacío si no existe.
     */
    suspend fun getDietaSemana(): Map<String, List<Meal>> = runCatching {
        val userId = currentUserId() ?: return emptyMap()
        val snapshot = db.collection("users")
            .document(userId)
            .collection("dieta_general")
            .document("comidas")
            .get()
            .await()

        val data = snapshot.data ?: return emptyMap()

        parseDietaData(data)
    }.getOrDefault(emptyMap())

    // ---------------------------------------------------------
    // Funciones para el nutricionista
    // ---------------------------------------------------------

    /**
     * Sube o actualiza una dieta personalizada para un cliente.
     *
     * @param clienteId ID del cliente.
     * @param dieta Mapa de días a listas de comidas ([Meal]).
     */
    suspend fun subirDieta(clienteId: String, dieta: Map<String, List<Meal>>) {
        runCatching {
            val dietaRef = db.collection("users")
                .document(clienteId)
                .collection("dieta_general")
                .document("comidas")

            val data = dieta.mapValues {
                it.value.map { comida ->
                    mapOf("tipo" to comida.tipo, "alimentos" to comida.alimentos)
                }
            }

            dietaRef.set(data).await()
        }
    }

    /**
     * Obtiene la dieta actual de un cliente específico por su ID.
     *
     * @param clienteId ID del cliente.
     * @return Mapa de días con listas de comidas, o vacío si no hay datos.
     */
    suspend fun obtenerDieta(clienteId: String): Map<String, List<Meal>> = runCatching {
        val snapshot = db.collection("users")
            .document(clienteId)
            .collection("dieta_general")
            .document("comidas")
            .get()
            .await()

        val data = snapshot.data ?: return@runCatching emptyMap()

        parseDietaData(data)
    }.getOrDefault(emptyMap())

    /**
     * Convierte un mapa de datos sin tipar desde Firestore en un mapa válido de [Meal].
     *
     * @param rawData Mapa con los datos originales desde Firestore.
     * @return Mapa convertido con listas de objetos [Meal].
     */
    private fun parseDietaData(rawData: Map<String, Any>): Map<String, List<Meal>> {
        return rawData.mapValues { (_, value) ->
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
