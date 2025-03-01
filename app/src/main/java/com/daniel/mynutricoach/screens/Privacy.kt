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
fun PrivacyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Política de Privacidad") },
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
                text = "Aquí va la política de privacidad...",
                fontSize = 16.sp
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Aceptar")
            }
        }
    }
}
