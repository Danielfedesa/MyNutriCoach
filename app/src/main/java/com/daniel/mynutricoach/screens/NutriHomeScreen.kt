package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.ui.components.cards.AppointmentSimpleCard
import com.daniel.mynutricoach.ui.components.buttons.NutriBottomNavBar
import com.daniel.mynutricoach.ui.components.cards.SectionTitle
import com.daniel.mynutricoach.viewmodel.NutriHomeViewModel

/**
 * Pantalla principal del nutricionista que muestra un resumen general de la actividad reciente.
 *
 * Incluye:
 * - Lista de citas del día actual.
 * - Lista de citas programadas para la semana.
 * - Total de clientes registrados.
 *
 * Esta pantalla sirve como "Home" para el nutricionista y permite una visualización rápida
 * del estado actual de su agenda y clientela.
 *
 * @param navController Controlador de navegación para cambiar entre pantallas.
 * @param homeViewModel ViewModel encargado de proporcionar los datos de citas y clientes.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriHomeScreen(
    navController: NavHostController,
    homeViewModel: NutriHomeViewModel = viewModel()
) {
    val todayAppointments by homeViewModel.todayAppointments.collectAsState()
    val weekAppointments by homeViewModel.weekAppointments.collectAsState()
    val totalClients by homeViewModel.totalClients.collectAsState()

    val sortedTodayAppointments = todayAppointments.sortedWith(compareBy({ it.fecha }, { it.hora }))
    val sortedWeekAppointments = weekAppointments.sortedWith(compareBy({ it.fecha }, { it.hora }))

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
                    title = { Text("") }, // Vacío por si quieres luego poner "Inicio"
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { NutriBottomNavBar(navController, "NutriHome") }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SectionTitle(title = "Citas de Hoy", icon = Icons.Default.CalendarToday)
            }
            items(sortedTodayAppointments) { cita ->
                AppointmentSimpleCard(
                    cliente = "${cita.clienteNombre} ${cita.clienteApellido}",
                    hora = cita.hora
                )
            }

            item {
                SectionTitle(title = "Citas de la Semana", icon = Icons.Default.DateRange)
            }
            items(sortedWeekAppointments) { cita ->
                AppointmentSimpleCard(
                    fecha = cita.fecha,
                    hora = cita.hora
                )
            }

            item {
                SectionTitle(title = "Clientes Registrados", icon = Icons.Default.Group)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$totalClients Clientes",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 34.dp)
                )
            }
        }
    }
}