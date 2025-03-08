package com.daniel.mynutricoach.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.navigation.AppScreens

@Composable
fun BottomNavBar(navController: NavHostController, currentScreen: String) {
    NavigationBar {
        listOf("Progress", "Dieta", "Citas", "Perfil").forEach { screen ->
            NavigationBarItem(
                selected = screen == currentScreen,
                onClick = { navController.navigate(screen) },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text(screen) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}
