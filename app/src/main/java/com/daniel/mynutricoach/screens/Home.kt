package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navController: NavHostController, auth: FirebaseAuth) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Menu Principal", fontSize = 24.sp)

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("UsuariosAlta") }) {
            Text(text = "Alta Usuario")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { navController.navigate("usuarioEliminar") }) {
            Text(text = "Eliminar Usuario")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { navController.navigate("usuarioModificar") }) {
            Text(text = "Modificar Usuario")
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}