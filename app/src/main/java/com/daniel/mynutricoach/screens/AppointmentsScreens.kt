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
fun AppointmentsComp(navController: NavHostController, appointmentsViewModel: AppointmentsViewModel = viewModel()) {

    val userName by appointmentsViewModel.userName.collectAsState()
    val appointments by appointmentsViewModel.appointments.collectAsState()

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
                    text = "No existe registro de citas",
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
        AppointmentState.Programada -> Color(0xFFBBDEFB)
        AppointmentState.Finalizada -> Color(0xFFC8E6C9 )
        AppointmentState.Cancelada -> Color(0xFFFFCDD2)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = appointment.estado.name, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Fecha: ${appointment.fecha}", color = Color.Black)
            Text(text = "Hora: ${appointment.hora}", color = Color.Black)
        }
    }
}
