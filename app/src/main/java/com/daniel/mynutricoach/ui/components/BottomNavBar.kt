package com.daniel.mynutricoach.ui.components

import androidx.compose.foundation.layout.height
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
                    selectedIconColor = Color(0xFF0077CC),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun NutriBottomNavBar(navController: NavHostController, currentScreen: String) {
    NavigationBar(
        containerColor = Color.White
    ) {
        listOf(
            Triple("NutriHome", "Home", Icons.Filled.Home),
            Triple("NutriClients", "Clientes", Icons.Filled.People),
            Triple("NutriAppointments", "Citas", Icons.Filled.CalendarToday),
            Triple("NutriProfile", "Perfil", Icons.Filled.Person)
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
                    selectedIconColor = Color(0xFF0077CC),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}


