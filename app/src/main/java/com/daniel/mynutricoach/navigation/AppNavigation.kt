package com.daniel.mynutricoach.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daniel.mynutricoach.screens.*
import com.daniel.mynutricoach.viewmodel.HomeViewModel
import com.daniel.mynutricoach.viewmodel.RegisterViewModel
import com.daniel.mynutricoach.viewmodel.UserProfileViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Login.ruta) {
        composable(AppScreens.Login.ruta) { LoginScreen(navController) }
        composable(AppScreens.Home.ruta) { Home(navController) }
        composable(AppScreens.Progress.ruta) { Progress() }

        // Pantalla de Registro con su ViewModel asociado
        composable(AppScreens.Register.ruta) {
            val registerViewModel: RegisterViewModel = viewModel()
            Register(navController, registerViewModel)
        }

        // Pantalla de Perfil Inicial con su propio ViewModel
        composable(AppScreens.InitialProfile.ruta) {
            val userProfileViewModel: UserProfileViewModel = viewModel()
            InitialProfile(navController, userProfileViewModel)
        }

        // Pantalla Home con su ViewModel asociado
        composable(AppScreens.Home.ruta) {
            val homeViewModel: HomeViewModel = viewModel()
            Home(navController, homeViewModel)
        }
    }
}