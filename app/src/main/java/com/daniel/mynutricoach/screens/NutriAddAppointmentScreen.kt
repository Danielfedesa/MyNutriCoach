package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.viewmodel.NutriAppointmentsViewModel
import java.time.LocalDate
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import com.daniel.mynutricoach.ui.components.DayCard
import com.daniel.mynutricoach.ui.components.TimeSlotButton


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NutriAddAppointmentComp(
    clienteId: String,
    clienteNombre: String,
    clienteApellido: String,
    navController: NavHostController,
    viewModel: NutriAppointmentsViewModel = viewModel()
) {
    val today = remember { LocalDate.now() }
    val selectedDate = remember { mutableStateOf(today) }
    val appointments by viewModel.appointments.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reservar cita para $clienteNombre") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Días (solo lunes a viernes)
            val diasDisponibles = (0..60)
                .map { today.plusDays(it.toLong()) }
                .filter { it.dayOfWeek.value in 1..5 } // 1 = Lunes, 5 = Viernes

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(diasDisponibles) { day ->
                    DayCard(
                        day = day,
                        isSelected = selectedDate.value == day,
                        onClick = { selectedDate.value = day }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Horas disponibles
            val horarios = listOf(
                "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30",
                "13:00", "13:30"
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                horarios.forEach { hora ->
                    val isTaken = appointments.any {
                        it.fecha == selectedDate.value.toString() && it.hora == hora
                    }
                    TimeSlotButton(hora, isTaken) {
                        if (!isTaken) {
                            viewModel.addAppointment(
                                clienteId = clienteId,
                                clienteNombre = clienteNombre,
                                clienteApellido = clienteApellido,
                                fecha = selectedDate.value.toString(),
                                hora = hora
                            ) {
                                showSuccessDialog = true
                            }
                        }
                    }
                }
            }

            // Mensaje de cita reservada
            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { showSuccessDialog = false },
                    title = { Text("Cita Reservada") },
                    text = { Text("La cita se ha reservado correctamente.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSuccessDialog = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}

