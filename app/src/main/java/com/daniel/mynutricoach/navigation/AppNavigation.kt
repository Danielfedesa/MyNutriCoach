package com.daniel.mynutricoach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daniel.mynutricoach.screens.*

@Composable
fun AppNavigation (){
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.Login.ruta)
    {
        composable(AppScreens.Login.ruta) { Login(navigationController) }
        composable(AppScreens.Home.ruta){ Home(navigationController) }
    }
}