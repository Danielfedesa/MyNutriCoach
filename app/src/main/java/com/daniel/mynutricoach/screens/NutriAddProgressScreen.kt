package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.viewmodel.ProgressViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProgressComp(
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

            CustomTextField(
                value = peso,
                onValueChange = { peso = it },
                label = "Peso (kg)",
            )

            CustomTextField(
                value = masaMuscular,
                onValueChange = { masaMuscular = it },
                label = "Masa Muscular (%)"
            )

            CustomTextField(
                value = grasa,
                onValueChange = { grasa = it },
                label = "Grasa Corporal (%)"
            )

            Button(
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
            ) {
                Text("Guardar Progreso")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Progreso Añadido") },
            text = { Text("El progreso se ha registrado correctamente.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
