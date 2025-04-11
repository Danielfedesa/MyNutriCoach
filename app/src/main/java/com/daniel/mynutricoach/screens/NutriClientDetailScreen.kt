package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.GraphComponent
import com.daniel.mynutricoach.viewmodel.NutriClientDetailViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriClientDetailComp(clienteId: String, navController: NavHostController, nutriClientDetailviewModel: NutriClientDetailViewModel = viewModel()) {
    val cliente by nutriClientDetailviewModel.cliente.collectAsState()
    val progreso by nutriClientDetailviewModel.progreso.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(clienteId) {
        nutriClientDetailviewModel.loadCliente(clienteId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    "Detalle del Cliente",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )  },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    ) { padding ->
        cliente?.let { clienteData ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${clienteData.nombre} ${clienteData.apellidos}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("Correo: ${clienteData.email}", fontSize = 18.sp)
                Text("Teléfono: ${clienteData.telefono}", fontSize = 18.sp)
                Text("Sexo: ${clienteData.sexo}" , fontSize = 18.sp)
                Text("F. nacimiento: ${clienteData.fechaNacimiento}" , fontSize = 18.sp)
                Text("Peso objetivo: ${clienteData.pesoObjetivo ?: "--"} Kg", fontSize = 18.sp)

                if (progreso.isNotEmpty()) {
                    Text("Peso actual: ${progreso.last().peso} Kg", fontSize = 18.sp)
                } else {
                    Text("Peso actual: -- Kg", fontSize = 18.sp)
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Historial de Peso (kg)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                if (progreso.isNotEmpty()) {
                    GraphComponent(progreso.map { it.timestamp to it.peso }, "")
                } else {
                    Text("No hay historial de progreso")
                }
            }
        } ?: run {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text("Cargando datos...")
            }
        }
    }

    // Diálogo para añadir progreso o dieta
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Qué quieres añadir?") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    navController.navigate("AñadirProgreso/$clienteId")
                }) {
                    Text("Progreso")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    navController.navigate("AñadirDieta/$clienteId")
                }) {
                    Text("Dieta")
                }
            }
        )
    }
}