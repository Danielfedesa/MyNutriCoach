package com.daniel.mynutricoach.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.ui.components.CustomTextField
import com.google.firebase.auth.FirebaseAuth


/*
    // Si el usuario ya está autenticado, navegamos a la pantalla Progress
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate("Progress") {
                popUpTo("Login") { inclusive = true }
            }
        }
    }
 */

    // Login Screen
    @Composable
    fun Login(navController: NavHostController, auth: FirebaseAuth) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen superior (30% de la pantalla)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f) // Espacio restante para el contenido
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Bienvenido",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo correo con la función CustomTextField
                CustomTextField(value = email, onValueChange = { email = it }, placeholder = "Correo")

                Spacer(Modifier.height(12.dp))

                // Campo contraseña con la función CustomTextField
                CustomTextField(value = password, onValueChange = { password = it }, placeholder = "Contraseña", isPassword = true)

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            Toast.makeText(context, "Funcionalidad no implementada", Toast.LENGTH_SHORT).show()
                        }
                )

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("Home")
                                    Log.i("Login", "Inicio de sesión correcto")
                                } else {
                                    val errorMsg = task.exception?.message ?: "Error desconocido"
                                    Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                                    Log.i("Login", errorMsg)
                                }
                            }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Iniciar sesión", fontSize = 18.sp, color = Color.White)
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(text = "¿No tienes una cuenta? ", fontSize = 14.sp, color = Color.DarkGray)
                    Text(
                        text = "Regístrate ahora",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate("Register")
                        }
                    )
                }
            }
        }
    }