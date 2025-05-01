package com.daniel.mynutricoach.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.repository.AppointmentsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

/**
 * ViewModel utilizado en la pantalla de inicio del nutricionista.
 *
 * Se encarga de obtener y mantener en estado los siguientes datos:
 * - Las citas programadas para hoy.
 * - Las citas programadas para los próximos 7 días.
 * - El número total de clientes registrados en Firebase.
 *
 * Utiliza [AppointmentsRepository] para gestionar las citas y acceso directo a Firestore para contar usuarios.
 *
 * @property todayAppointments Lista reactiva de citas del día actual.
 * @property weekAppointments Lista reactiva de citas de la semana.
 * @property totalClients Número total de usuarios registrados.
 */
@RequiresApi(Build.VERSION_CODES.O)
class NutriHomeViewModel(
    private val appointmentsRepository: AppointmentsRepository = AppointmentsRepository(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    /**
     * Lista reactiva de citas del día actual.
     * Se inicializa con una lista vacía y se actualiza al cargar las citas.
     */
    private val _todayAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val todayAppointments: StateFlow<List<Appointment>> = _todayAppointments

    /**
     * Lista reactiva de citas de la semana.
     * Se inicializa con una lista vacía y se actualiza al cargar las citas.
     */
    private val _weekAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val weekAppointments: StateFlow<List<Appointment>> = _weekAppointments

    /**
     * Número total de usuarios registrados.
     * Se inicializa en 0 y se actualiza al cargar los usuarios desde Firestore.
     */
    private val _totalClients = MutableStateFlow(0)
    val totalClients: StateFlow<Int> = _totalClients

    init {
        loadAppointments()
        loadClients()
    }

    /**
     * Carga todas las citas desde Firebase y filtra:
     * - Citas de hoy.
     * - Citas para los próximos 7 días.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadAppointments() {
        viewModelScope.launch {
            runCatching {
                val allAppointments = appointmentsRepository.getAllAppointments()
                val today = LocalDate.now()
                val endOfWeek = today.plusDays(7)

                _todayAppointments.value = allAppointments.filter { it.fecha == today.toString() }
                _weekAppointments.value = allAppointments.filter {
                    val fecha = LocalDate.parse(it.fecha)
                    fecha.isAfter(today.minusDays(1)) && fecha.isBefore(endOfWeek)
                }
            }.onFailure {
                _todayAppointments.value = emptyList()
                _weekAppointments.value = emptyList()
            }
        }
    }

    /**
     * Carga el número total de clientes desde Firestore.
     * Actualiza el estado [_totalClients] con el número de documentos en la colección "users".
     */
    private fun loadClients() {
        viewModelScope.launch {
            runCatching {
                val snapshot = db.collection("users").get().await()
                _totalClients.value = snapshot.size()
            }.onFailure {
                _totalClients.value = 0
            }
        }
    }
}
