package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.ui.components.buttons.BottomNavBar
import com.daniel.mynutricoach.ui.components.cards.DayMeals
import com.daniel.mynutricoach.ui.components.cards.getDayName
import com.daniel.mynutricoach.viewmodel.DietsViewModel

/**
 * Pantalla de visualización de la dieta semanal del cliente.
 *
 * Permite al usuario navegar entre los días de la semana y consultar la dieta asignada para cada día.
 * Utiliza un [DietsViewModel] para recuperar el nombre del usuario y la dieta desde Firestore.
 *
 * Componentes clave:
 * - Barra superior con banner.
 * - Navegación entre días usando botones de flecha.
 * - Muestra el nombre del día actual mediante [getDayName].
 * - Componente [DayMeals] que lista las comidas correspondientes al día actual.
 * - Barra inferior de navegación con la sección activa marcada como "Diets".
 *
 * @param navController Controlador de navegación de Jetpack Compose.
 * @param dietsViewModel ViewModel que gestiona el estado de la dieta y el nombre del usuario.
 */
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClientDietsScreen(
    navController: NavHostController,
    dietsViewModel: DietsViewModel = viewModel()
) {
    val userName by dietsViewModel.userName.collectAsState()
    var dayOffset by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.banner),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
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
                text = "Dieta de $userName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { dayOffset-- }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Día Anterior"
                    )
                }

                Text(
                    text = getDayName(dayOffset),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = { dayOffset++ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Día Siguiente"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            DayMeals(dayOffset, dietsViewModel, navController)
        }
    }
}


