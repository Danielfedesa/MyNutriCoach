package com.daniel.mynutricoach.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daniel.mynutricoach.screens.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    // Recuerda el estado de la navegación cuando se recomponen los composables
    val navController = rememberNavController()

    // Contenedor principal de la navegación con las rutas de la app
    NavHost(navController = navController, startDestination = AppScreens.Login.ruta) {
        composable(AppScreens.Login.ruta) { LoginComp(navController) }

        // Terminos y Condiciones
        composable(AppScreens.Terms.ruta) { TermsComp(navController) }
        composable(AppScreens.Privacy.ruta) { PrivacyComp(navController) }

        // Pantalla de Registro con su ViewModel asociado
        composable(AppScreens.Register.ruta) { RegisterComp(navController) }

        // Pantalla de Perfil Inicial con su propio ViewModel
        composable(AppScreens.InitialProfile.ruta) { InitialProfileComp(navController) }

        // Pantallas del cliente

        // Pantalla Progress(Home del cliente) con su ViewModel asociado
        composable(AppScreens.Progress.ruta) { ProgressComp(navController) }

        // Pantalla Diets con su ViewModel asociado
        composable(AppScreens.Diets.ruta) { DietsComp(navController) }

        // Pantalla FoodDetail con su ViewModel asociado
        composable(AppScreens.FoodDetail.ruta) { FoodDetailComp(navController) }

        // Pantalla Appointments
        composable(AppScreens.Appointments.ruta) { AppointmentsComp(navController) }

        // Pantalla Profile con su ViewModel asociado
        composable(AppScreens.Profile.ruta) { ProfileComp(navController) }


        // Screens del nutricionista

        // Pantalla Home con su ViewModel asociado
        composable(AppScreens.Home.ruta) { HomeComp(navController) }

    }
}