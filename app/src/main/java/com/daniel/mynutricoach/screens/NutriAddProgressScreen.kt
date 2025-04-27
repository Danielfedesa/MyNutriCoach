package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
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
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.dialogues.SuccessDialog
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.viewmodel.ProgressViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriAddProgressScreen(
    clienteId: String,
    navController: NavHostController,
    viewModel: ProgressViewModel = viewModel()
) {
    var peso by remember { mutableStateOf("") }
    var masaMuscular by remember { mutableStateOf("") }
    var grasa by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Añadir Progreso",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(value = peso, onValueChange = { peso = it }, label = "Peso (kg)")
            CustomTextField(value = masaMuscular, onValueChange = { masaMuscular = it }, label = "Masa Muscular (%)")
            CustomTextField(value = grasa, onValueChange = { grasa = it }, label = "Grasa Corporal (%)")

            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(
                text = "Guardar Progreso",
                onClick = {
                    if (peso.isNotBlank() && masaMuscular.isNotBlank() && grasa.isNotBlank()) {
                        viewModel.addProgress(
                            clienteId = clienteId,
                            peso = peso.replace(",", ".").toFloat(),
                            masaMuscular = masaMuscular.replace(",", ".").toFloat(),
                            grasa = grasa.replace(",", ".").toFloat()
                        ) {
                            showDialog = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (showDialog) {
        SuccessDialog(
            title = "Progreso Añadido",
            message = "El progreso se ha registrado correctamente.",
            onDismiss = { showDialog = false },
            onConfirm = {
                showDialog = false
                navController.popBackStack()
            }
        )
    }
}
