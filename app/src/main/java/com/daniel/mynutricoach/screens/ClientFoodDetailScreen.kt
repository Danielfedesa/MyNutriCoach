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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.BottomNavBar
import com.daniel.mynutricoach.ui.components.cards.NutrientCard
import com.daniel.mynutricoach.viewmodel.FoodDetailViewModel

/**
 * Pantalla de detalle nutricional de alimentos seleccionados.
 *
 * Esta pantalla muestra la información nutricional obtenida de la API de Nutritionix
 * para una lista de alimentos seleccionados. Los valores mostrados están estandarizados
 * por cada 100g de alimento. Está pensada para que el cliente visualice el aporte nutricional
 * de su dieta.
 *
 * Comportamiento destacado:
 * - Carga automática de los nutrientes al entrar en pantalla mediante [LaunchedEffect].
 * - Presentación de tarjetas individuales por alimento usando [NutrientCard].
 * - Indicador de carga mientras se obtienen los datos desde la API.
 * - Botón de navegación para volver atrás y barra inferior con "Diets" seleccionada.
 *
 * @param navController Controlador de navegación utilizado para gestionar la navegación.
 * @param foodDetailViewModel ViewModel encargado de obtener los nutrientes desde la API.
 * @param tipo Tipo de comida (por ejemplo: desayuno, comida, cena) que se está visualizando.
 * @param alimentos Lista de nombres de alimentos para los cuales se mostrarán los nutrientes.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientFoodDetailScreen(
    navController: NavHostController,
    foodDetailViewModel: FoodDetailViewModel = viewModel(),
    tipo: String,
    alimentos: List<String>
) {
    val nutrientes by foodDetailViewModel.nutrientes.collectAsState()

    LaunchedEffect(true) {
        foodDetailViewModel.cargarNutrientes(alimentos)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Información Nutricional",
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
        bottomBar = { BottomNavBar(navController, "Diets") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Valores por cada 100g",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (nutrientes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                nutrientes.forEach { info ->
                    NutrientCard(info)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
