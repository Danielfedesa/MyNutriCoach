package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.viewmodel.NutriDietViewModel

/**
 * Pantalla utilizada por el nutricionista para visualizar la dieta asignada a un cliente.
 *
 * Esta pantalla muestra:
 * - Una lista de los días de la semana.
 * - Dentro de cada día, los tipos de comida (Desayuno, Almuerzo, etc.) junto a los alimentos correspondientes.
 * - Un mensaje si el cliente no tiene dieta asignada.
 *
 * @param clienteId ID único del cliente cuya dieta se desea visualizar.
 * @param navController Controlador de navegación para gestionar la navegación entre pantallas.
 * @param nutriDietViewModel ViewModel que maneja la lógica de obtención y gestión de dietas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriViewDietScreen(
    clienteId: String,
    navController: NavHostController,
    nutriDietViewModel: NutriDietViewModel = viewModel()
) {
    val dietaSemana by nutriDietViewModel.dietaSemana.collectAsState()
    val diasSemana =
        listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val tiposComida = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")

    LaunchedEffect(clienteId) {
        nutriDietViewModel.cargarDieta(clienteId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Dieta del Cliente",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (dietaSemana.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Este cliente no tiene dieta registrada.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                diasSemana.forEach { dia ->
                    Text(
                        text = dia,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    tiposComida.forEach { tipo ->
                        val alimentos =
                            dietaSemana[dia]?.find { it.tipo == tipo }?.alimentos ?: emptyList()
                        if (alimentos.isNotEmpty()) {
                            Text(
                                text = "$tipo: ${alimentos.joinToString(", ")}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, bottom = 4.dp)
                            )
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}