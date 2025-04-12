package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
            TopAppBar(
                title = { Text("Añadir Progreso") },
                colors = TopAppBarDefaults.topAppBarColors(
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
            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (Kg)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = masaMuscular,
                onValueChange = { masaMuscular = it },
                label = { Text("Masa Muscular (%)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = grasa,
                onValueChange = { grasa = it },
                label = { Text("Grasa Corporal (%)") },
                modifier = Modifier.fillMaxWidth()
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
