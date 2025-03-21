package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.daniel.mynutricoach.ui.components.BottomNavBar
import com.daniel.mynutricoach.viewmodel.DietsViewModel
import java.time.LocalDate
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietsComp(navController: NavHostController, dietsViewModel: DietsViewModel = viewModel()) {

    val userName by dietsViewModel.userName.collectAsState()

    val todayIndex = 100 // Punto central del carrusel
    val listSize = 200   // Días antes y después

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = todayIndex)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                modifier = Modifier.height(120.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.banner),
                        contentDescription = "Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            )
        },
        bottomBar = { BottomNavBar(navController, "Diets") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = "Dietas de ${userName ?: "Usuario"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(count = listSize) { index ->
                    val dayOffset = index - todayIndex
                    DayMeals(
                        dayOffset = dayOffset,
                        dietsViewModel = dietsViewModel,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayMeals(dayOffset: Int, dietsViewModel: DietsViewModel, modifier: Modifier = Modifier) {
    val meals by dietsViewModel.getMealsForDay(dayOffset).collectAsState()

    val diaTexto = remember(dayOffset) {
        val diasSemana = listOf("lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo")
        val date = LocalDate.now().plusDays(dayOffset.toLong())
        val dia = date.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale("es", "ES")).lowercase()

        // Si no es un día válido, devolver un string vacío
        if (dia !in diasSemana) "Día no disponible" else dia.replaceFirstChar { it.uppercaseChar() }
    }

    println("🖥️ Comidas en UI para $diaTexto: $meals") // Debug

    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = diaTexto, // Solo muestra el día (ej: "Lunes")
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (meals.isEmpty()) {
            Text(text = "No hay comidas asignadas para este día")
        } else {
            meals.forEach { meal -> MealCard(meal) }
        }
    }
}

@Composable
fun MealCard(meal: Meal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4C3))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = meal.tipo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            meal.alimentos?.joinToString(", ")?.let {
                Text(text = it, fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}