package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.viewmodel.NutriDietViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriViewDietComp(
    clienteId: String,
    navController: NavHostController,
    nutriDietViewModel: NutriDietViewModel = viewModel()
) {
    val dietaSemana by nutriDietViewModel.dietaSemana.collectAsState()
    val diasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val tiposComida = listOf("Desayuno", "Almuerzo", "Comida", "Merienda", "Cena")

    LaunchedEffect(clienteId) {
        nutriDietViewModel.cargarDieta(clienteId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dieta del Cliente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("<", fontSize = 30.sp, color = Color.White)
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
                .verticalScroll(rememberScrollState())
        ) {
            if (dietaSemana.isEmpty()) {
                Text("Este cliente no tiene dieta registrada.")
            } else {
                diasSemana.forEach { dia ->
                    Text(
                        text = dia,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    tiposComida.forEach { tipo ->
                        val alimentos = dietaSemana[dia]?.find { it.tipo == tipo }?.alimentos ?: emptyList()
                        if (alimentos.isNotEmpty()) {
                            Text("$tipo: ${alimentos.joinToString(", ")}")
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}