package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.cards.DayCard
import com.daniel.mynutricoach.ui.components.buttons.TimeSlotButton
import com.daniel.mynutricoach.ui.components.dialogues.SuccessDialog
import com.daniel.mynutricoach.viewmodel.NutriAppointmentsViewModel
import java.time.LocalDate

/**
 * Pantalla utilizada por el nutricionista para añadir una nueva cita con un cliente específico.
 *
 * Permite:
 * - Seleccionar una fecha disponible (solo días laborales dentro de los próximos 60 días).
 * - Elegir una franja horaria libre.
 * - Confirmar la cita con un cliente específico (nombre, apellido, ID).
 * - Mostrar un diálogo de éxito tras la creación de la cita.
 *
 * El estado de las citas ya programadas se utiliza para bloquear franjas horarias ocupadas.
 *
 * @param clienteId ID único del cliente con quien se agenda la cita.
 * @param clienteNombre Nombre del cliente.
 * @param clienteApellido Apellido del cliente.
 * @param navController Controlador de navegación para volver atrás tras confirmar.
 * @param viewModel ViewModel que gestiona las citas y el estado de disponibilidad.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NutriAddAppointmentScreen(
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Añadir Cita",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            val diasDisponibles = (0..60)
                .map { today.plusDays(it.toLong()) }
                .filter { it.dayOfWeek.value in 1..5 }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(diasDisponibles) { day ->
                    DayCard(
                        day = day,
                        isSelected = selectedDate.value == day,
                        onClick = { selectedDate.value = day }
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            val horarios = listOf(
                "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30",
                "13:00", "13:30", "14:00", "14:30"
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(20.dp)
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

            if (showSuccessDialog) {
                SuccessDialog(
                    title = "Cita Reservada",
                    message = "La cita se ha reservado correctamente.",
                    onDismiss = { showSuccessDialog = false },
                    onConfirm = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}