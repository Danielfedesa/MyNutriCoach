package com.daniel.mynutricoach.screens

import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.navigation.AppScreens
import com.daniel.mynutricoach.ui.components.visuals.GraphComponent
import com.daniel.mynutricoach.viewmodel.NutriClientDetailViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriClientDetailComp(
    clienteId: String,
    navController: NavHostController,
    nutriClientDetailviewModel: NutriClientDetailViewModel = viewModel()
) {
    val cliente by nutriClientDetailviewModel.cliente.collectAsState()
    val progreso by nutriClientDetailviewModel.progreso.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(clienteId) {
        nutriClientDetailviewModel.loadCliente(clienteId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Detalle del cliente",
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
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 12.dp
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir",
                        modifier = Modifier.size(40.dp)
                    )
                }
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
                Text("Sexo: ${clienteData.sexo}", fontSize = 18.sp)
                Text("F. nacimiento: ${clienteData.fechaNacimiento}", fontSize = 18.sp)
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

    // Diálogo para añadir progreso, dieta o cita
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Qué quieres añadir?", fontSize = 22.sp) },
            text = { Text("Selecciona qué deseas registrar para este cliente:", fontSize = 18.sp) },
            confirmButton = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                            navController.navigate("AñadirProgreso/$clienteId")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Progreso", fontSize = 18.sp)
                    }

                    Button(
                        onClick = {
                            showDialog = false
                            cliente?.let { clienteData ->
                                navController.navigate("${AppScreens.NutriAddDiet.ruta}/${clienteData.userId}")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dieta", fontSize = 18.sp)
                    }

                    Button(
                        onClick = {
                            showDialog = false
                            cliente?.let { clienteData ->
                                navController.navigate("NutriAddAppointment/${clienteData.userId}/${Uri.encode(clienteData.nombre)}/${Uri.encode(clienteData.apellidos)}")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cita", fontSize = 18.sp)
                    }
                }
            }
        )
    }
}
