package com.daniel.mynutricoach.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.daniel.mynutricoach.screens.*
import com.daniel.mynutricoach.viewmodel.HomeViewModel
import com.daniel.mynutricoach.viewmodel.InitialProfileViewModel
import com.daniel.mynutricoach.viewmodel.ProgressViewModel
import com.daniel.mynutricoach.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Login.ruta) {
        composable(AppScreens.Login.ruta) { LoginScreen(navController) }
        composable(AppScreens.Home.ruta) { Home(navController) }

        // Pantalla de Registro con su ViewModel asociado
        composable(AppScreens.Register.ruta) {
            val registerViewModel: RegisterViewModel = viewModel()
            Register(navController, registerViewModel)
        }

        // Pantalla de Perfil Inicial con su propio ViewModel
        composable(AppScreens.InitialProfile.ruta) {
            val initialProfileViewModel: InitialProfileViewModel = viewModel()
            InitialProfile(navController, initialProfileViewModel)
        }

        // Pantalla Progress(Home del cliente) con su ViewModel asociado
        composable(AppScreens.Progress.ruta) {
            val progressViewModel: ProgressViewModel = viewModel()
            Progress(navController, progressViewModel) }

        // Pantalla Home con su ViewModel asociado
        composable(AppScreens.Home.ruta) {
            val homeViewModel: HomeViewModel = viewModel()
            Home(navController, homeViewModel)
        }

        // Terminos y Condiciones
        composable(AppScreens.Terms.ruta) { TermsScreen(navController) }
        composable(AppScreens.Privacy.ruta) { PrivacyScreen(navController) }

    }
}