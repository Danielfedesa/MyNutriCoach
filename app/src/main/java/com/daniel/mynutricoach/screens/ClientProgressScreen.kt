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
import com.daniel.mynutricoach.ui.components.visuals.ProgressComponent
import com.daniel.mynutricoach.viewmodel.ProgressViewModel

/**
 * Pantalla de progreso del cliente.
 *
 * Muestra al usuario un resumen de su historial físico, incluyendo:
 * - Peso actual
 * - Masa muscular
 * - Porcentaje de grasa corporal
 *
 * Cada uno de estos parámetros se representa en gráficos personalizados mediante [ProgressComponent].
 * Además, se presenta un saludo personalizado con el nombre del usuario y un carrusel visual en la parte superior.
 *
 * Características:
 * - Accede a los datos del usuario desde el [ProgressViewModel].
 * - Implementa scroll vertical para visualizar componentes en pantallas pequeñas.
 * - Si no hay progreso registrado, muestra un mensaje informativo.
 * - Utiliza [BottomNavBar] para la navegación persistente y [CarouselComponent] como cabecera visual.
 *
 * @param navController Controlador de navegación usado para moverse entre pantallas.
 * @param progressViewModel ViewModel que gestiona y proporciona el historial de progreso del usuario.
 */
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
                text = "Hola, $userName",
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

                ProgressComponent("Peso actual", latest.peso, "kg", pesos)
                ProgressComponent("Masa muscular", latest.masaMuscular, "kg", masas)
                ProgressComponent("Porcentaje de grasa", latest.grasa, "%", grasas)
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


