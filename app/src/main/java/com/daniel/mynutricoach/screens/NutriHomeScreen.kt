package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.viewmodel.HomeViewModel

@Composable
fun Home(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val userEmail = homeViewModel.getCurrentUserEmail()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a la Home")
        Text(text = "Usuario: ${userEmail ?: "No autenticado"}")

        Button(onClick = { navController.navigate("Progress") }) {
            Text(text = "Ir a Progress")
        }
    }
}