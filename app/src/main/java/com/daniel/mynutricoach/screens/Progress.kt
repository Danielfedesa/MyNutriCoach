package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daniel.mynutricoach.viewmodel.ProgressViewModel
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.ui.components.CarouselComponent
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.GraphComponent


@Composable
fun Progress(navController: NavHostController, progressViewModel: ProgressViewModel = viewModel()) {

    val userName by progressViewModel.userName.collectAsState()
    val progressHistory by progressViewModel.progressHistory.collectAsState()

    Scaffold(
        topBar = { CarouselComponent() },
        bottomBar = { BottomNavBar(navController, "Progress") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hola, $userName 👋",
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            if (progressHistory.isNotEmpty()) {
                val latestProgress = progressHistory.last() // Último registro de progreso

                val pesos = progressHistory.map { it.timestamp to it.peso }
                val masas = progressHistory.map { it.timestamp to it.masa_muscular }
                val grasas = progressHistory.map { it.timestamp to it.grasa }

                Text(
                    text = "Peso actual: ${"%.1f".format(latestProgress.peso)} kg",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                GraphComponent(pesos, "kg")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Masa muscular: ${"%.1f".format(latestProgress.masa_muscular)} kg",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                GraphComponent(masas, "kg")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Porcentaje de grasa: ${"%.1f".format(latestProgress.grasa)} %",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                GraphComponent(grasas, "%")
            } else {
                Text(
                    text = "No hay historial de datos. Tu nutricionista aún no ha registrado tu progreso.",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


