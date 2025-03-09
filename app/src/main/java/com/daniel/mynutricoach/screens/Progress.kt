package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
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
        topBar = {
            Column { // 🔹 Colocamos el carrusel dentro de una `Column` para que se renderice bien
                CarouselComponent()
            }
        },
        bottomBar = { BottomNavBar(navController, "Progress") } // 🔹 Se mantiene la barra de navegación inferior
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
                val latestPeso = progressHistory.lastOrNull()?.get("peso") as? Float ?: 0f
                val latestMasa = progressHistory.lastOrNull()?.get("masa_muscular") as? Float ?: 0f
                val latestGrasa = progressHistory.lastOrNull()?.get("grasa") as? Float ?: 0f

                val pesos = progressHistory.map { (it["timestamp"] as? Long ?: 0L) to (it["peso"] as? Float ?: 0f) }
                val masas = progressHistory.map { (it["timestamp"] as? Long ?: 0L) to (it["masa_muscular"] as? Float ?: 0f) }
                val grasas = progressHistory.map { (it["timestamp"] as? Long ?: 0L) to (it["grasa"] as? Float ?: 0f) }

                Text(
                    text = "Peso actual: ${"%.1f".format(latestPeso)} kg",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                GraphComponent(pesos, "kg")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Masa muscular: ${"%.1f".format(latestMasa)} kg",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                GraphComponent(masas, "kg")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Porcentaje de grasa: ${"%.1f".format(latestGrasa)} %",
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                GraphComponent(grasas, "%")
            } else {
                Text(
                    text = "No hay historial de datos",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

