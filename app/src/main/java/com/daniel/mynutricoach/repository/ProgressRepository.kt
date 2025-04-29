package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.models.Progress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de gestionar los registros de progreso físico de los usuarios.
 *
 * Permite:
 * - Obtener el historial de progreso del usuario autenticado.
 * - Obtener el progreso de un cliente específico (usado por el nutricionista).
 * - Añadir un nuevo registro de progreso para un cliente.
 *
 * @property auth Instancia de [FirebaseAuth] para acceder al usuario autenticado.
 * @property db Instancia de [FirebaseFirestore] para acceder a los datos en Firestore.
 */
class ProgressRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /**
     * Obtiene el historial de progreso del usuario autenticado.
     *
     * Los registros se ordenan por fecha (timestamp).
     *
     * @return Lista de [Progress] o vacía si no hay registros.
     */
    suspend fun getProgressHistory(): List<Progress> = runCatching {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId)
                .collection("progress")
                .orderBy("timestamp")
                .get()
                .await()
                .documents.mapNotNull { it.toObject(Progress::class.java) }
        }
    }.getOrNull() ?: emptyList()

    /**
     * Añade un nuevo registro de progreso al historial de un cliente específico.
     *
     * @param clienteId ID del cliente al que se añadirá el registro.
     * @param progress Objeto [Progress] con los datos del nuevo progreso.
     */
    suspend fun addProgress(clienteId: String, progress: Progress) {
        runCatching {
            db.collection("users")
                .document(clienteId)
                .collection("progress")
                .add(progress)
                .await()
        }
    }

    /**
     * Obtiene el historial de progreso de un cliente específico.
     *
     * @param clienteId ID del cliente.
     * @return Lista de [Progress] o vacía si no existen registros.
     */
    suspend fun getProgresoCliente(clienteId: String): List<Progress> = runCatching {
        db.collection("users")
            .document(clienteId)
            .collection("progress")
            .orderBy("timestamp")
            .get()
            .await()
            .documents.mapNotNull { it.toObject(Progress::class.java) }
    }.getOrDefault(emptyList())
}
