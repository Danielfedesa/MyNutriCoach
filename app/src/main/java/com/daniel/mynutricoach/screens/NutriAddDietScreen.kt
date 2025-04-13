package com.daniel.mynutricoach.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.ui.components.inputs.DropdownMenuSelector
import com.daniel.mynutricoach.viewmodel.NutriDietViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RestrictedApi")
@Composable
fun NutriAddDietScreen(
    clienteId: String, viewModel: NutriDietViewModel = viewModel(), navController: NavHostController
) {
    val diasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val tiposComida = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")

    var alimento by remember { mutableStateOf("") }
    var alimentosTemp = remember { mutableStateListOf<String>() }
    var diaSeleccionado by remember { mutableStateOf(diasSemana.first()) }
    var tipoSeleccionado by remember { mutableStateOf(tiposComida.first()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Añadir Dieta",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            DropdownMenuSelector("Día", diasSemana, diaSeleccionado) {
                diaSeleccionado = it
            }

            Spacer(Modifier.height(8.dp))

            DropdownMenuSelector("Tipo", tiposComida, tipoSeleccionado) {
                tipoSeleccionado = it
            }

            CustomTextField(
                value = alimento,
                onValueChange = { alimento = it },
                label = "Alimento",
            )

            Button(onClick = {
                if (alimento.isNotBlank()) {
                    alimentosTemp.add(alimento)
                    alimento = ""
                }
            }) {
                Text("Añadir alimento")
            }

            Spacer(Modifier.height(12.dp))
            Text("Alimentos: ${alimentosTemp.joinToString(", ")}")

            Button(
                onClick = {
                    viewModel.actualizarComida(
                        diaSeleccionado,
                        tipoSeleccionado,
                        alimentosTemp.toList()
                    )
                    alimentosTemp.clear()
                }
            ) {
                Text("Guardar Comida")
            }

            Spacer(Modifier.height(24.dp))

            Button(onClick = {
                viewModel.guardarDieta(clienteId) {
                    navController.popBackStack()
                }
            }) {
                Text("Guardar Dieta Completa")
            }
        }
    }
}

