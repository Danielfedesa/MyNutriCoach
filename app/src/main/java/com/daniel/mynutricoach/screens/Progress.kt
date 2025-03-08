package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daniel.mynutricoach.viewmodel.ProgressViewModel
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.ui.components.CarouselComponent
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.GraphComponent


@Composable
fun Progress(navController: NavHostController, progressViewModel: ProgressViewModel = viewModel()) {

    val progressHistory by progressViewModel.progressHistory.collectAsState()

    Scaffold(bottomBar = { BottomNavBar(navController, "Progress") }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CarouselComponent()

            Spacer(modifier = Modifier.height(16.dp))

            if (progressHistory.isNotEmpty()) {
                val latestPeso = progressHistory.lastOrNull()?.get("peso") as? Float ?: 0f
                val latestMasa = progressHistory.lastOrNull()?.get("masa_muscular") as? Float ?: 0f
                val latestGrasa = progressHistory.lastOrNull()?.get("grasa") as? Float ?: 0f

                val pesos = progressHistory.map { (it["timestamp"] as? Long ?: 0L) to (it["peso"] as? Float ?: 0f) }
                val masas = progressHistory.map { (it["timestamp"] as? Long ?: 0L) to (it["masa_muscular"] as? Float ?: 0f) }
                val grasas = progressHistory.map { (it["timestamp"] as? Long ?: 0L) to (it["grasa"] as? Float ?: 0f) }

                // Mostrar valores más recientes antes de cada gráfica
                Text(
                    text = "Peso actual: ${"%.1f".format(latestPeso)} kg", // Peso con el ultimo valor
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                GraphComponent(pesos, "kg")

                Text(
                    text = "Masa muscular: ${"%.1f".format(latestMasa)} kg", // Masa muscular con el ultimo valor
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                GraphComponent(masas, "kg")

                Text(
                    text = "Porcentaje de grasa: ${"%.1f".format(latestGrasa)} %", // Grasa con el ultimo valor
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                GraphComponent(grasas, "%")
            } else {
                Text("No hay historial de datos", modifier = Modifier.padding(16.dp))
            }
        }
    }
}