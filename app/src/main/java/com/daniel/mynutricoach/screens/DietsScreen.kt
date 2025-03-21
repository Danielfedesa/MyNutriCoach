package com.daniel.mynutricoach.screens

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.navigation.AppScreens
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.viewmodel.DietsViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DietsComp(navController: NavHostController, dietsViewModel: DietsViewModel = viewModel()) {
    val userName by dietsViewModel.userName.collectAsState()

    // Este estado se reinicia cada vez que se navega de nuevo a esta pantalla
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
                    contentDescription = "Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { BottomNavBar(navController, "Diets") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dietas de ${userName ?: "Usuario"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            // Botones de navegación por día
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

            // Mostrar las comidas del día
            DayMeals(
                dayOffset, dietsViewModel,
                navController = navController
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayName(dayOffset: Int): String {
    val date = LocalDate.now().plusDays(dayOffset.toLong())
    return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercaseChar() }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayMeals(offset: Int, dietsViewModel: DietsViewModel, navController: NavHostController) {
    val meals by dietsViewModel.getMealsForDay(offset).collectAsState()

    if (meals.isEmpty()) {
        Text(
            text = "No hay comidas asignadas para este día",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            meals.forEach { meal ->
                MealCard(meal) {
                    val encodedAlimentos = Uri.encode(meal.alimentos.joinToString("|"))
                    navController.navigate("${AppScreens.FoodDetail.ruta}/${meal.tipo}/$encodedAlimentos")
                }
            }
        }
    }
}


@Composable
fun MealCard(meal: Meal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = meal.tipo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text = meal.alimentos.joinToString(", "),
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

