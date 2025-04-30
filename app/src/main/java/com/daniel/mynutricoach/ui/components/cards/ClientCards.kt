package com.daniel.mynutricoach.ui.components.cards

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

/**
 * Tarjeta que muestra información básica de una cita ([Appointment]).
 *
 * El color de fondo varía según el estado de la cita:
 * - Azul claro para Programada
 * - Verde claro para Finalizada
 * - Rojo claro para Cancelada
 *
 * @param appointment Objeto de tipo [Appointment] con los datos de la cita.
 */
@Composable
fun AppointmentCard(appointment: Appointment) {
    val backgroundColor = when (appointment.estado) {
        AppointmentState.Programada -> Color(0xFFBBDEFB)
        AppointmentState.Finalizada -> Color(0xFFC8E6C9)
        AppointmentState.Cancelada -> Color(0xFFFFCDD2)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = appointment.estado.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(text = "Fecha: ${appointment.fecha}", color = Color.Black)
            Text(text = "Hora: ${appointment.hora}", color = Color.Black)
        }
    }
}

/**
 * Tarjeta que muestra los valores nutricionales de un alimento ([FoodInfo]).
 *
 * Incluye información sobre calorías, proteínas, carbohidratos y grasas por cada 100g.
 *
 * @param info Objeto [FoodInfo] con la información nutricional del alimento.
 */
@Composable
fun NutrientCard(info: FoodInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEBEBEB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = info.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Calorías: ${info.calories} kcal",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Proteínas: ${info.protein} g",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Carbohidratos: ${info.carbs} g",
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Grasas: ${info.fat} g",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

/**
 * Muestra las comidas del día seleccionado con sus respectivos alimentos.
 *
 * Las comidas se ordenan según el tipo (desayuno, comida, etc.).
 * Al pulsar en una comida, se navega a la pantalla de detalles nutricionales.
 *
 * @param offset Diferencia de días desde hoy (0 = hoy, 1 = mañana...).
 * @param dietsViewModel ViewModel que gestiona las comidas.
 * @param navController Controlador de navegación para redirigir a la pantalla de detalles.
 */
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
            val orderedMeals = meals.sortedBy { meal ->
                when (meal.tipo.lowercase()) {
                    "desayuno" -> 0
                    "almuerzo" -> 1
                    "comida" -> 2
                    "merienda" -> 3
                    "cena" -> 4
                    else -> 5
                }
            }

            orderedMeals.forEach { meal ->
                MealCard(meal) {
                    val encodedAlimentos = Uri.encode(meal.alimentos.joinToString("|"))
                    navController.navigate("${AppScreens.FoodDetail.ruta}/${meal.tipo}/$encodedAlimentos")
                }
            }
        }
    }
}

/**
 * Tarjeta que muestra el tipo de comida y los alimentos incluidos.
 *
 * Es interactiva y permite ejecutar una acción (por ejemplo, navegar a detalles).
 *
 * @param meal Objeto [Meal] que contiene el tipo y lista de alimentos.
 * @param onClick Acción a ejecutar al pulsar sobre la tarjeta.
 */
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
            Text(
                text = meal.tipo,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = meal.alimentos.joinToString(", "),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

/**
 * Devuelve el nombre del día de la semana correspondiente a un offset desde hoy.
 *
 * @param dayOffset Número de días a sumar/restar desde la fecha actual.
 * @return Nombre del día de la semana en español con la primera letra en mayúscula.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun getDayName(dayOffset: Int): String {
    val date = LocalDate.now().plusDays(dayOffset.toLong())
    return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercaseChar() }
}
