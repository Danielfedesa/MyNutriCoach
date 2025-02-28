package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp


@Composable
    fun InitialProfile(navController: NavHostController, db: FirebaseFirestore, userId: String) {
        var nombre by remember { mutableStateOf("") }
        var apellidos by remember { mutableStateOf("") }
        var fechaNacimiento by remember { mutableStateOf("") }
        var sexo by remember { mutableStateOf("") }
        var estatura by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Nombre", fontSize = 20.sp)
            TextField(value = nombre, onValueChange = { nombre = it }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            Text(text = "Apellido", fontSize = 20.sp)
            TextField(value = apellidos, onValueChange = { apellidos = it }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            Text(text = "Fecha de Nacimiento", fontSize = 20.sp)
            TextField(value = fechaNacimiento, onValueChange = { fechaNacimiento = it }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            Text(text = "Sexo", fontSize = 20.sp)
            TextField(value = sexo, onValueChange = { sexo = it }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(8.dp))

            Text(text = "Estatura (cm)", fontSize = 20.sp)
            TextField(value = estatura, onValueChange = { estatura = it }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                val userProfile = hashMapOf(
                    "nombre" to nombre,
                    "apellido" to apellidos,
                    "fechaNacimiento" to fechaNacimiento,
                    "sexo" to sexo,
                    "estatura" to estatura
                )

                db.collection("users").document(userId)
                    .set(userProfile)
                    .addOnSuccessListener {
                        navController.navigate("progress") {
                            popUpTo("perfilInicial") { inclusive = true }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al guardar datos", Toast.LENGTH_SHORT).show()
                    }
            }) {
                Text(text = "Guardar")
            }
        }
    }
