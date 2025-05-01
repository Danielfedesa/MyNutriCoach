package com.daniel.mynutricoach.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Progress
import com.daniel.mynutricoach.repository.ProgressRepository
import com.daniel.mynutricoach.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar los datos de progreso del usuario (peso, masa muscular, grasa corporal).
 *
 * Se conecta con [ProgressRepository] para recuperar el historial de progreso y añadir nuevos registros.
 * También obtiene el nombre del usuario desde [UserRepository] para mostrarlo en la UI.
 *
 * @property progressHistory Historial completo de progreso del usuario como lista de objetos [Progress].
 * @property userName Nombre del usuario actual.
 */
class ProgressViewModel(
    private val progressRepository: ProgressRepository = ProgressRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    /**
     * Historial de progreso del usuario.
     * Se inicializa con una lista vacía y se actualiza al cargar el ViewModel.
     */
    private val _progressHistory = MutableStateFlow<List<Progress>>(emptyList())
    val progressHistory: StateFlow<List<Progress>> = _progressHistory

    /**
     * Nombre del usuario actual.
     * Se inicializa con un valor por defecto y se actualiza al cargar el ViewModel.
     */
    private val _userName = MutableStateFlow("Usuario")
    val userName: StateFlow<String> = _userName

    init {
        // Carga inicial del historial y del nombre del usuario
        viewModelScope.launch {
            _progressHistory.value = progressRepository.getProgressHistory()
            _userName.value = userRepository.getUserName() ?: "Usuario"
        }
    }

    /**
     * Agrega un nuevo registro de progreso para un cliente específico en Firebase.
     *
     * @param clienteId ID del cliente al que pertenece el progreso.
     * @param peso Peso actual en kilogramos.
     * @param masaMuscular Porcentaje de masa muscular.
     * @param grasa Porcentaje de grasa corporal.
     * @param onSuccess Callback que se ejecuta si la operación es exitosa.
     */
    fun addProgress(
        clienteId: String,
        peso: Float,
        masaMuscular: Float,
        grasa: Float,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                val progress = Progress(
                    timestamp = System.currentTimeMillis(),
                    peso = peso,
                    masaMuscular = masaMuscular,
                    grasa = grasa
                )
                progressRepository.addProgress(clienteId, progress)
            }.onSuccess {
                onSuccess()
            }
        }
    }
}