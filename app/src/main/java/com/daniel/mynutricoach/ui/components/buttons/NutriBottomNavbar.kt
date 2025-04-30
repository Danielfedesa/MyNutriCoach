package com.daniel.mynutricoach.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

/**
 * Barra de navegación inferior específica para usuarios con rol de nutricionista.
 *
 * Proporciona acceso rápido a las secciones principales:
 * - Inicio
 * - Clientes
 * - Citas
 * - Perfil
 *
 * Resalta la pantalla actualmente activa.
 *
 * @param navController Controlador de navegación usado para gestionar el cambio de pantalla.
 * @param currentScreen Nombre de la pantalla actual, usado para resaltar el ítem correspondiente.
 */
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
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}