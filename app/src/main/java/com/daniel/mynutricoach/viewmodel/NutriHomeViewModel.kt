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

@RequiresApi(Build.VERSION_CODES.O)
class NutriHomeViewModel(
    private val appointmentsRepository: AppointmentsRepository = AppointmentsRepository(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _todayAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val todayAppointments: StateFlow<List<Appointment>> = _todayAppointments

    private val _weekAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val weekAppointments: StateFlow<List<Appointment>> = _weekAppointments

    private val _totalClients = MutableStateFlow(0)
    val totalClients: StateFlow<Int> = _totalClients

    init {
        loadAppointments()
        loadClients()
    }

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
