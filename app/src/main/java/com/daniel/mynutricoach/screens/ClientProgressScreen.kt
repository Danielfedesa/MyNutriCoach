package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.BottomNavBar
import com.daniel.mynutricoach.ui.components.visuals.CarouselComponent
import com.daniel.mynutricoach.ui.components.visuals.ProgressSection
import com.daniel.mynutricoach.viewmodel.ProgressViewModel


@Composable
fun ClientProgressScreen(
    navController: NavHostController,
    progressViewModel: ProgressViewModel = viewModel()
) {
    val userName by progressViewModel.userName.collectAsState()
    val progressHistory by progressViewModel.progressHistory.collectAsState()

    Scaffold(
        topBar = { CarouselComponent() },
        bottomBar = { BottomNavBar(navController, "Progress") }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hola, $userName 👋",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp)
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
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


