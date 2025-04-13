package com.daniel.mynutricoach.ui.components.cards

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.AppointmentState
import com.daniel.mynutricoach.models.FoodInfo
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.navigation.AppScreens
import com.daniel.mynutricoach.viewmodel.DietsViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

// función para mostrar cada cita en una tarjeta en la pantalla Appointments (Cliente)
@Composable
fun AppointmentCard(appointment: Appointment) {
    val backgroundColor = when (appointment.estado) {
        AppointmentState.Programada -> Color(0xFFBBDEFB)
        AppointmentState.Finalizada -> Color(0xFFC8E6C9 )
        AppointmentState.Cancelada -> Color(0xFFFFCDD2)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = appointment.estado.name, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Fecha: ${appointment.fecha}", color = Color.Black)
            Text(text = "Hora: ${appointment.hora}", color = Color.Black)
        }
    }
}

// Función para mostrar cada tarjeta de nutrientes
// Recibe un objeto FoodInfo y muestra su información
// Se usa en la pantalla FoodDetailComp
@Composable
fun NutrientCard(info: FoodInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = info.name, fontWeight = FontWeight.Bold)
            Text(text = "Calorías: ${info.calories}")
            Text(text = "Proteínas: ${info.protein} gr")
            Text(text = "Carbohidratos: ${info.carbs} gr")
            Text(text = "Grasas: ${info.fat} gr")
        }
    }
}

// Función para obtener el nombre del día de la semana en español
// Se usa en la pantalla DietsScreen para mostrar el nombre del día actual
@RequiresApi(Build.VERSION_CODES.O)
fun getDayName(dayOffset: Int): String {
    val date = LocalDate.now().plusDays(dayOffset.toLong())
    return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercaseChar() }
}

// Función para mostrar las comidas del día
// Se usa en la pantalla DietsScreen para mostrar las comidas asignadas para el día actual
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

// Función para mostrar cada comida en una tarjeta
// Se usa en la pantalla DietsComp para mostrar cada comida asignada
@Composable
fun MealCard(meal: Meal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEBEBEB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = meal.tipo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text = meal.alimentos.joinToString(", "),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

