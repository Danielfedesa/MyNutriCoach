package com.daniel.mynutricoach.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.ui.components.inputs.DropdownMenuSelector
import com.daniel.mynutricoach.viewmodel.NutriDietViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.daniel.mynutricoach.ui.components.buttons.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriAddDietScreen(
    clienteId: String,
    viewModel: NutriDietViewModel = viewModel(),
    navController: NavHostController
) {
    val diasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val tiposComida = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")
    val dietaSemana by viewModel.dietaSemana.collectAsState()

    var alimento by remember { mutableStateOf("") }
    var diaActual by remember { mutableStateOf(diasSemana.first()) }
    var tipoActual by remember { mutableStateOf(tiposComida.first()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Añadir Dieta", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text(
                            text = "<",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Selector día y tipo de comida
            DropdownMenuSelector("Día", diasSemana, diaActual) { diaActual = it }
            DropdownMenuSelector("Tipo", tiposComida, tipoActual) { tipoActual = it }

            CustomTextField(
                value = alimento,
                onValueChange = { alimento = it },
                label = "Nuevo alimento"
            )

            CustomButton(
                text = "Añadir alimento",
                onClick = {
                    if (alimento.isNotBlank()) {
                        val nuevosAlimentos = (dietaSemana[diaActual]?.find { it.tipo == tipoActual }?.alimentos ?: emptyList()).toMutableList()
                        nuevosAlimentos.add(alimento)
                        viewModel.actualizarComida(diaActual, tipoActual, nuevosAlimentos)
                        alimento = ""
                    }
                },
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(20.dp))

            // Tabla visual de la dieta completa
            Text("Resumen de la Dieta", style = MaterialTheme.typography.titleMedium)

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                diasSemana.forEach { dia ->
                    Text(dia, fontWeight = FontWeight.Bold)

                    tiposComida.forEach { tipo ->
                        val alimentos = dietaSemana[dia]?.find { it.tipo == tipo }?.alimentos ?: emptyList()
                        if (alimentos.isNotEmpty()) {
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text("$tipo:", fontWeight = FontWeight.SemiBold)
                                Text(alimentos.joinToString(", "), fontSize = 14.sp)
                            }
                        }
                    }
                    HorizontalDivider()
                }
            }

            Spacer(Modifier.height(32.dp))

            CustomButton(
                text = "Guardar dieta",
                onClick = {
                    viewModel.guardarDieta(clienteId) {
                        navController.popBackStack()
                    }
                },
                containerColor = Color(0xFF4CAF50),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

