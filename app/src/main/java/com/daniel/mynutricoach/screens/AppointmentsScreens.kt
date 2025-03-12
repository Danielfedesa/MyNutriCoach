package com.daniel.mynutricoach.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.daniel.mynutricoach.viewmodel.AppointmentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Appointments(navController: NavHostController, appointmentsViewModel: AppointmentsViewModel = viewModel()) {

    val userName by appointmentsViewModel.userName.collectAsState()
    val appointments by appointmentsViewModel.appointments.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                modifier = Modifier.height(120.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.banner), // Asegúrate de tener esta imagen en res/drawable
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            )
        },
        bottomBar = { BottomNavBar(navController, "Appointments") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Citas de ${userName ?: "Usuario"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            if (appointments.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No tienes citas programadas",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            } else {
                LazyColumn {
                    items(appointments) { cita -> AppointmentCard(cita) }
                }
            }
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
    val backgroundColor = when (appointment.estado) {
        AppointmentState.Programada -> Color(0xFF4A90E2)
        AppointmentState.Finalizada -> Color(0xFF7ED321)
        AppointmentState.Cancelada -> Color(0xFFD0021B)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = appointment.estado.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Fecha: ${appointment.fecha}", color = Color.White)
            Text(text = "Hora: ${appointment.hora}", color = Color.White)
        }
    }
}
