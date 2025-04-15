package com.daniel.mynutricoach.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.cards.NutriAppointmentCard
import com.daniel.mynutricoach.ui.components.buttons.NutriBottomNavBar
import com.daniel.mynutricoach.viewmodel.NutriAppointmentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriAppointmentsComp(
    navController: NavHostController,
    viewModel: NutriAppointmentsViewModel = viewModel()
) {
    val appointments by viewModel.appointments.collectAsState()

    var showFinalizeDialog by remember { mutableStateOf(false) }
    var showCancelConfirmationDialog by remember { mutableStateOf(false) }
    var selectedAppointmentId by remember { mutableStateOf<String?>(null) }
    val sortedAppointments = appointments.sortedWith(
        compareBy({ it.fecha }, { it.hora })
    )

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
                    modifier = Modifier
                        .fillMaxSize()
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
                        showCancelConfirmationDialog = true
                    }
                )
            }
        }

        // Mostrar mensaje de "Cita finalizada correctamente"
        if (showFinalizeDialog) {
            AlertDialog(
                onDismissRequest = { showFinalizeDialog = false },
                confirmButton = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomButton(
                            text = "Aceptar",
                            onClick = {
                                selectedAppointmentId?.let { id ->
                                    viewModel.updateAppointmentStatus(
                                        id,
                                        AppointmentState.Finalizada
                                    )
                                }
                                showFinalizeDialog = false
                            },
                            modifier = Modifier.width(220.dp)
                        )
                    }
                },
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
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
                text = {
                    Text(
                        text = "La cita ha finalizado correctamente.",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }

        // Confirmar cancelación de cita
        if (showCancelConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showCancelConfirmationDialog = false },
                title = {
                    Text(
                        "Cancelar Cita",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        "¿Estás seguro de que quieres cancelar esta cita?",
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                confirmButton = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomButton(
                            text = "Sí, cancelar",
                            onClick = {
                                selectedAppointmentId?.let { id ->
                                    viewModel.updateAppointmentStatus(
                                        id,
                                        AppointmentState.Cancelada
                                    )
                                }
                                showCancelConfirmationDialog = false
                            },
                            modifier = Modifier.width(220.dp)
                        )
                    }
                },
                dismissButton = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomButton(
                            text = "No",
                            onClick = { showCancelConfirmationDialog = false },
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            modifier = Modifier.width(220.dp)
                        )
                    }
                }
            )
        }
    }
}

