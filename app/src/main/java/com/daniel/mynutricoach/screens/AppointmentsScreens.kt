package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.viewmodel.AppointmentsViewModel

@Composable
fun Appointments(navController: NavHostController, appointmentsViewModel: AppointmentsViewModel = viewModel()) {

    val userName by appointmentsViewModel.userName.collectAsState()
    val appointments by appointmentsViewModel.appointments.collectAsState()

    Scaffold(bottomBar = { BottomNavBar(navController, "Appointments") }) { paddingValues ->
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
                color = Color.Black
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
        AppointmentState.Programada -> Color(0xFF007BFF)
        AppointmentState.Finalizada -> Color(0xFF28A745)
        AppointmentState.Cancelada -> Color(0xFFDC3545)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = appointment.estado.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "Fecha: ${appointment.fecha}", color = Color.White)
            Text(text = "Hora: ${appointment.hora}", color = Color.White)
        }
    }
}