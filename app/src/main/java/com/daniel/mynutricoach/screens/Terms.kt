package com.daniel.mynutricoach.screens

import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TermsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar( // ✅ Material 2, sin APIs experimentales
                title = { Text("Términos y Condiciones") },
                backgroundColor = MaterialTheme.colors.primarySurface
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Aquí van los términos y condiciones...",
                fontSize = 16.sp
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Aceptar")
            }
        }
    }
}