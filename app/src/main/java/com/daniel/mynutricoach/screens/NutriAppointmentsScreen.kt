package com.daniel.mynutricoach.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.ui.components.NutriBottomNavBar
import com.daniel.mynutricoach.viewmodel.NutriAppointmentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriAppointmentsComp(
    navController: NavHostController,
    viewModel: NutriAppointmentsViewModel = viewModel()
) {
    val appointments by viewModel.appointments.collectAsState()

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
            items(appointments) { cita ->
                NutriAppointmentCard(cita,
                    onFinalize = {
                        viewModel.updateAppointmentStatus(cita.id, AppointmentState.Finalizada)
                    },
                    onCancel = {
                        viewModel.updateAppointmentStatus(cita.id, AppointmentState.Cancelada)
                    }
                )
            }
        }
    }
}

@Composable
fun NutriAppointmentCard(
    appointment: Appointment,
    onFinalize: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (appointment.estado) {
                AppointmentState.Programada -> Color(0xFFBBDEFB)
                AppointmentState.Finalizada -> Color(0xFFC8E6C9)
                AppointmentState.Cancelada -> Color(0xFFFFCDD2)
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Cliente: ${appointment.userName}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Estado: ${appointment.estado.name}", fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Fecha",
                    tint = Color(0xFF1976D2), // azul bonito
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = appointment.fecha)
            }

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Hora",
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(text = appointment.hora)
            }

            Spacer(Modifier.height(8.dp))

            if (appointment.estado == AppointmentState.Programada) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onFinalize,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Finalizar")
                    }
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}
