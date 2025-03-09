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
                modifier = Modifier.padding(bottom = 24.dp).align(Alignment.CenterHorizontally),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            if (progressHistory.isNotEmpty()) {
                val latest = progressHistory.last()
                val pesos = progressHistory.map { it.timestamp to it.peso }
                val masas = progressHistory.map { it.timestamp to it.masaMuscular }
                val grasas = progressHistory.map { it.timestamp to it.grasa }

                ProgressSection("Peso actual", latest.peso, "kg", pesos)
                ProgressSection("Masa muscular", latest.masaMuscular, "kg", masas)
                ProgressSection("Porcentaje de grasa", latest.grasa, "%", grasas)

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

@Composable
fun ProgressSection(title: String, value: Float, unit: String, data: List<Pair<Long, Float>>) {
    Text(
        text = "$title: ${"%.1f".format(value)} $unit",
        modifier = Modifier.padding(start = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    GraphComponent(data, unit)
    Spacer(modifier = Modifier.height(16.dp))
}


