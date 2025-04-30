package com.daniel.mynutricoach.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Barra de navegación inferior para usuarios cliente.
 *
 * Muestra iconos y etiquetas que permiten navegar entre las secciones principales de la app:
 * Progreso, Dieta, Citas y Perfil. Resalta el ítem correspondiente a la pantalla actual.
 *
 * @param navController Controlador de navegación usado para cambiar de pantalla.
 * @param currentScreen Nombre de la pantalla actual para marcar el ítem como seleccionado.
 */
@Composable
fun BottomNavBar(navController: NavHostController, currentScreen: String) {
    NavigationBar(
        containerColor = Color.White
    ) {
        listOf(
            Triple("Progress", "Progreso", Icons.Filled.BarChart),
            Triple("Diets", "Dieta", Icons.Filled.Restaurant),
            Triple("Appointments", "Citas", Icons.Filled.CalendarToday),
            Triple("Profile", "Perfil", Icons.Filled.Person)
        ).forEach { (screen, label, icon) ->
            NavigationBarItem(
                selected = screen == currentScreen, // Marca la pantalla actual
                onClick = { navController.navigate(screen) }, // Navega a la pantalla
                icon = {
                    Icon(icon, contentDescription = label, modifier = Modifier.size(22.dp))
                },
                label = {
                    Text(label, fontSize = 12.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}


