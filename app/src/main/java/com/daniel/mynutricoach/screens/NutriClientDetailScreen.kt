package com.daniel.mynutricoach.screens

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.daniel.mynutricoach.navigation.AppScreens
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.dialogues.DialogButton
import com.daniel.mynutricoach.ui.components.visuals.GraphComponent
import com.daniel.mynutricoach.utils.obtenerEdad
import com.daniel.mynutricoach.viewmodel.NutriClientDetailViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriClientDetailScreen(
    clienteId: String,
    navController: NavHostController,
    nutriClientDetailViewModel: NutriClientDetailViewModel = viewModel()
) {
    val cliente by nutriClientDetailViewModel.cliente.collectAsState()
    val progreso by nutriClientDetailViewModel.progreso.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(clienteId) {
        nutriClientDetailViewModel.loadCliente(clienteId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Detalle del cliente",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(8.dp, 12.dp),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 16.dp)
                    .size(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    ) { padding ->
        cliente?.let { clienteData ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(14.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("${clienteData.nombre} ${clienteData.apellidos}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Correo: ${clienteData.email}", fontSize = 18.sp)
                Text("Teléfono: ${clienteData.telefono}", fontSize = 18.sp)

                val edad = if (clienteData.fechaNacimiento.isNotBlank()) {
                    obtenerEdad(clienteData.fechaNacimiento).toString()
                } else {
                    "--"
                }
                Text("Edad: $edad años", fontSize = 18.sp)

                Text("Peso objetivo: ${clienteData.pesoObjetivo ?: "--"} Kg", fontSize = 18.sp)

                if (progreso.isNotEmpty()) {
                    Text("Peso actual: ${progreso.last().peso} Kg", fontSize = 18.sp)
                } else {
                    Text("Peso actual: -- Kg", fontSize = 18.sp)
                }

                CustomButton(
                    text = "Ver Dieta",
                    onClick = { navController.navigate("NutriViewDiet/${clienteData.userId}") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Historial de Peso (kg)", fontWeight = FontWeight.Bold, fontSize = 20.sp)

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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "¿Qué quieres añadir?",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    "Selecciona qué deseas registrar para este cliente:",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DialogButton("Progreso") {
                        showDialog = false
                        navController.navigate("AñadirProgreso/$clienteId")
                    }
                    DialogButton("Dieta") {
                        showDialog = false
                        cliente?.let { navController.navigate("${AppScreens.NutriAddDiet.ruta}/${it.userId}") }
                    }
                    DialogButton("Cita") {
                        showDialog = false
                        cliente?.let {
                            navController.navigate(
                                "NutriAddAppointment/${it.userId}/${Uri.encode(it.nombre)}/${Uri.encode(it.apellidos)}"
                            )
                        }
                    }
                }
            }
        )
    }
}
