package com.daniel.mynutricoach.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.ui.components.cards.NutriAppointmentCard
import com.daniel.mynutricoach.ui.components.buttons.NutriBottomNavBar
import com.daniel.mynutricoach.ui.components.dialogues.CustomAlertDialog
import com.daniel.mynutricoach.viewmodel.NutriAppointmentsViewModel

/**
 * Pantalla destinada al nutricionista para gestionar y visualizar las citas programadas con sus clientes.
 *
 * Permite:
 * - Listar las citas ordenadas cronológicamente.
 * - Finalizar una cita ya realizada.
 * - Cancelar una cita futura.
 *
 * Las citas se obtienen desde el [NutriAppointmentsViewModel] y su estado se puede modificar mediante acciones directas en cada tarjeta.
 *
 * @param navController Controlador de navegación para manejar transiciones de pantalla.
 * @param viewModel ViewModel encargado de la lógica de citas del nutricionista.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriAppointmentsScreen(
    navController: NavHostController,
    viewModel: NutriAppointmentsViewModel = viewModel()
) {
    val appointments by viewModel.appointments.collectAsState()

    var showFinalizeDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }
    var selectedAppointmentId by remember { mutableStateOf<String?>(null) }
    val sortedAppointments = appointments.sortedWith(compareBy({ it.fecha }, { it.hora }))

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { NutriBottomNavBar(navController, "NutriAppointments") }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(sortedAppointments) { cita ->
                NutriAppointmentCard(
                    appointment = cita,
                    onFinalize = {
                        selectedAppointmentId = cita.id
                        showFinalizeDialog = true
                    },
                    onCancel = {
                        selectedAppointmentId = cita.id
                        showCancelDialog = true
                    }
                )
            }
        }
    }

    if (showFinalizeDialog) {
        CustomAlertDialog(
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Cita finalizada",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cita Finalizada",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            },
            message = {
                Text(
                    text = "La cita ha finalizado correctamente.",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmText = "Aceptar",
            onConfirm = {
                selectedAppointmentId?.let { id ->
                    viewModel.updateAppointmentStatus(id, AppointmentState.Finalizada)
                }
                showFinalizeDialog = false
            },
            onDismiss = { showFinalizeDialog = false }
        )
    }

    if (showCancelDialog) {
        CustomAlertDialog(
            title = {
                Text(
                    text = "Cancelar Cita",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            message = {
                Text(
                    text = "¿Estás seguro de que quieres cancelar esta cita?",
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            confirmText = "Sí, cancelar",
            onConfirm = {
                selectedAppointmentId?.let { id ->
                    viewModel.updateAppointmentStatus(id, AppointmentState.Cancelada)
                }
                showCancelDialog = false
            },
            dismissText = "No",
            onDismiss = { showCancelDialog = false }
        )
    }
}

