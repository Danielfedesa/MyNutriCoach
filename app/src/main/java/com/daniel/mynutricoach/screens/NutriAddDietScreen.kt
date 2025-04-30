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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.ui.components.inputs.DropdownMenuSelector
import com.daniel.mynutricoach.viewmodel.NutriDietViewModel
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.dialogues.SuccessDialog

/**
 * Pantalla para nutricionistas que permite crear y asignar una dieta personalizada a un cliente.
 *
 * Funcionalidades:
 * - Seleccionar día de la semana y tipo de comida (desayuno, comida, cena, etc.).
 * - Introducir alimentos personalizados para cada tipo de comida.
 * - Visualizar un resumen editable de la dieta semanal actual.
 * - Guardar la dieta en Firestore, asociada al cliente correspondiente.
 *
 * Esta pantalla hace uso del `NutriDietViewModel` para gestionar el estado y persistencia de los datos.
 *
 * @param clienteId ID único del cliente al que se le asignará la dieta.
 * @param viewModel ViewModel que gestiona la dieta semanal y sus actualizaciones.
 * @param navController Controlador de navegación para gestionar la navegación de retorno.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriAddDietScreen(
    clienteId: String,
    viewModel: NutriDietViewModel = viewModel(),
    navController: NavHostController
) {
    val diasSemana =
        listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val tiposComida = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")
    val dietaSemana by viewModel.dietaSemana.collectAsState()

    var alimento by remember { mutableStateOf("") }
    var diaActual by remember { mutableStateOf(diasSemana.first()) }
    var tipoActual by remember { mutableStateOf(tiposComida.first()) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Añadir Dieta",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
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
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
                        val nuevosAlimentos =
                            (dietaSemana[diaActual]?.find { it.tipo == tipoActual }?.alimentos
                                ?: emptyList()).toMutableList()
                        nuevosAlimentos.add(alimento)
                        viewModel.actualizarComida(diaActual, tipoActual, nuevosAlimentos)
                        alimento = ""
                    }
                },
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(20.dp))

            Text("Resumen de la Dieta", style = MaterialTheme.typography.titleMedium)

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                diasSemana.forEach { dia ->
                    Text(dia, fontWeight = FontWeight.Bold)

                    tiposComida.forEach { tipo ->
                        val alimentos =
                            dietaSemana[dia]?.find { it.tipo == tipo }?.alimentos ?: emptyList()
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
                        showSuccessDialog = true
                    }
                },
                containerColor = Color(0xFF4CAF50),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }

    if (showSuccessDialog) {
        SuccessDialog(
            title = "Dieta Guardada",
            message = "La dieta se ha registrado correctamente.",
            onDismiss = { showSuccessDialog = false },
            onConfirm = {
                showSuccessDialog = false
                navController.popBackStack()
            }
        )
    }
}

