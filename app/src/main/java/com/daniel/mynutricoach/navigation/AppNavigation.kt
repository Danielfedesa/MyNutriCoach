package com.daniel.mynutricoach.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.daniel.mynutricoach.screens.*


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    // Recuerda el estado de la navegación cuando se recomponen los composables
    val navController = rememberNavController()

    // Contenedor principal de la navegación con las rutas de la app
    NavHost(navController = navController, startDestination = AppScreens.Login.ruta) {
        composable(AppScreens.Login.ruta) { LoginComp(navController) }

        // Terminos, Condiciones y Politica de Privacidad y ayuda
        composable(AppScreens.Terms.ruta) { TermsComp(navController) }
        composable(AppScreens.Privacy.ruta) { PrivacyComp(navController) }
        composable(AppScreens.Help.ruta) { HelpComp(navController) }

        // Pantalla de Registro con su ViewModel asociado
        composable(AppScreens.Register.ruta) { RegisterComp(navController) }

        // Pantalla de Perfil Inicial con su propio ViewModel
        composable(AppScreens.InitialProfile.ruta) { InitialProfileComp(navController) }

        // Pantallas del cliente

        // Pantalla Progress(Home del cliente) con su ViewModel asociado
        composable(AppScreens.Progress.ruta) { ProgressComp(navController) }

        // Pantalla Diets con su ViewModel asociado
        composable(AppScreens.Diets.ruta) { DietsComp(navController) }

        // Pantalla FoodDetail con argumentos
        composable(
            route = "${AppScreens.FoodDetail.ruta}/{tipo}/{alimentos}",
            arguments = listOf(
                navArgument("tipo") { type = NavType.StringType },
                navArgument("alimentos") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: ""
            val alimentosRaw = backStackEntry.arguments?.getString("alimentos") ?: ""
            val alimentos = alimentosRaw.split("|").map { it.trim() }

            FoodDetailComp(
                tipo = tipo,
                alimentos = alimentos,
                navController = navController
            )
        }

        // Pantalla Appointments
        composable(AppScreens.Appointments.ruta) { AppointmentsComp(navController) }

        // Pantalla Profile con su ViewModel asociado
        composable(AppScreens.Profile.ruta) { ProfileComp(navController) }

        // Pantalla EditProfile con su ViewModel asociado
        composable(AppScreens.EditProfile.ruta) { EditProfileComp(navController) }

        // Pantalla EditLanguage con su ViewModel asociado
        composable(AppScreens.EditLanguage.ruta) { EditLanguageComp(navController) }

        // Pantalla EditNotifications con su ViewModel asociado
        composable(AppScreens.EditNotifications.ruta) { EditNotificationsComp(navController) }


        // Screens del nutricionista

        // Pantalla Home con su ViewModel asociado
        composable(AppScreens.NutriHome.ruta) { NutriHomeComp(navController) }

        // Pantalla Clients con su ViewModel asociado
        composable(AppScreens.NutriClients.ruta) { NutriClientsComp(navController) }

        // Pantalla NutriClientDetail con su ViewModel asociado
        composable(
            route = "${AppScreens.NutriClientDetail.ruta}/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
            NutriClientDetailComp(clienteId = clienteId, navController = navController)
        }



    }
}

