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

/**
 * Define la navegación principal de la aplicación.
 *
 * Esta función configura todas las rutas posibles utilizando `NavHost`, incluyendo las pantallas
 * tanto para usuarios cliente como para nutricionistas. Cada pantalla está asociada a una ruta específica.
 *
 * - Utiliza [rememberNavController] para gestionar el estado de navegación.
 * - Define rutas que pueden recibir argumentos como IDs de clientes o listas de alimentos codificadas.
 * - Separa claramente las secciones de clientes y nutricionistas para facilitar el mantenimiento.
 *
 * @RequiresApi Se requiere Android O (API 26) debido al uso de funcionalidades basadas en fechas.
 * @OptIn Marca que se usan APIs experimentales de Material3.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    // Recuerda el estado de la navegación cuando se recomponen los composables
    val navController = rememberNavController()

    // Contenedor principal de la navegación con las rutas de la app
    NavHost(navController = navController, startDestination = AppScreens.Login.ruta) {
        composable(AppScreens.Login.ruta) { LoginScreen(navController) }

        // Terminos, Condiciones y Politica de Privacidad y ayuda
        composable(AppScreens.Terms.ruta) { TermsScreen(navController) }
        composable(AppScreens.Privacy.ruta) { PrivacyScreen(navController) }
        composable(AppScreens.Help.ruta) { HelpScreen(navController) }

        // Pantalla de Registro con su ViewModel asociado
        composable(AppScreens.Register.ruta) { RegisterScreen(navController) }

        // Pantalla de Perfil Inicial con su propio ViewModel
        composable(AppScreens.InitialProfile.ruta) { InitialProfileScreen(navController) }

        // Pantallas del cliente

        // Pantalla Progress(Home del cliente) con su ViewModel asociado
        composable(AppScreens.Progress.ruta) { ClientProgressScreen(navController) }

        // Pantalla Diets con su ViewModel asociado
        composable(AppScreens.Diets.ruta) { ClientDietsScreen(navController) }

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

            ClientFoodDetailScreen(
                tipo = tipo,
                alimentos = alimentos,
                navController = navController
            )
        }

        // Pantalla Appointments
        composable(AppScreens.Appointments.ruta) { ClientAppointmentsScreen(navController) }

        // Pantalla Profile con su ViewModel asociado
        composable(AppScreens.Profile.ruta) { ClientProfileScreen(navController) }

        // Pantalla EditProfile con su ViewModel asociado
        composable(AppScreens.EditProfile.ruta) { ClientEditProfileScreen(navController) }

        // Pantalla EditLanguage con su ViewModel asociado
        composable(AppScreens.EditLanguage.ruta) { EditLanguageScreen(navController) }

        // Pantalla EditNotifications con su ViewModel asociado
        composable(AppScreens.EditNotifications.ruta) { EditNotificationsScreen(navController) }


        // Screens del nutricionista

        // Pantalla Home con su ViewModel asociado
        composable(AppScreens.NutriHome.ruta) { NutriHomeScreen(navController) }

        // Pantalla Clients con su ViewModel asociado
        composable(AppScreens.NutriClients.ruta) { NutriClientsScreen(navController) }

        // Pantalla NutriClientDetail con su ViewModel asociado
        composable(
            route = "${AppScreens.NutriClientDetail.ruta}/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
            NutriClientDetailScreen(clienteId = clienteId, navController = navController)
        }

        // Pantalla NutriAddAppointment con su ViewModel asociado
        composable(
            route = "${AppScreens.NutriAddAppointment.ruta}/{clienteId}/{clienteNombre}/{clienteApellido}",
            arguments = listOf(
                navArgument("clienteId") { type = NavType.StringType },
                navArgument("clienteNombre") { type = NavType.StringType },
                navArgument("clienteApellido") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
            val clienteNombre = backStackEntry.arguments?.getString("clienteNombre") ?: ""
            val clienteApellido = backStackEntry.arguments?.getString("clienteApellido") ?: ""

            NutriAddAppointmentScreen(
                clienteId = clienteId,
                clienteNombre = clienteNombre,
                clienteApellido = clienteApellido,
                navController = navController
            )
        }

        // Pantalla NutriAddProgress con su ViewModel asociado
        composable(
            route = "AñadirProgreso/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
            NutriAddProgressScreen(clienteId = clienteId, navController = navController)
        }

        // Pantalla NutriAppointments con su ViewModel asociado
        composable(AppScreens.NutriAppointments.ruta) { NutriAppointmentsScreen(navController) }

        // Pantalla NutriProfile con su ViewModel asociado
        composable(AppScreens.NutriProfile.ruta) { NutriProfileScreen(navController) }

        // Pantalla NutriEditProfile con su ViewModel asociado
        composable(AppScreens.NutriEditProfile.ruta) { NutriEditProfileScreen(navController) }

        // Pantalla NutriAddDiet con su ViewModel asociado
        composable(
            route = "${AppScreens.NutriAddDiet.ruta}/{clienteId}",
            arguments = listOf(
                navArgument("clienteId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
            NutriAddDietScreen(clienteId = clienteId, navController = navController)
        }

        // Pantalla NutriViewDiet con su ViewModel asociado
        composable(
            route = "${AppScreens.NutriViewDiet.ruta}/{clienteId}",
            arguments = listOf(navArgument("clienteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
            NutriViewDietScreen(clienteId = clienteId, navController = navController)
        }
    }
}

