package com.daniel.mynutricoach.screens

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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.R
import com.daniel.mynutricoach.ui.components.CustomTextField
import com.daniel.mynutricoach.viewmodel.LoginViewModel

    // Login Screen
    @Composable
    fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val context = LocalContext.current

        // Estados del ViewModel
        val isAuthenticated by loginViewModel.isAuthenticated.collectAsState()
        val errorMessage by loginViewModel.errorMessage.collectAsState()

        /*
        // Verifica si hay una sesión activa al abrir la app
        LaunchedEffect(key1 = Unit) {
            if (isAuthenticated == null) { // Evita ejecutar checkSession múltiples veces
                loginViewModel.checkSession { route ->
                    navController.navigate(route) {
                        popUpTo("Login") { inclusive = true }
                    }
                }
            }
        }
*/
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
                        loginViewModel.signIn(email, password) { route ->
                            navController.navigate(route) {
                                popUpTo("Login") { inclusive = true }
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

                // Muestra un mensaje de error si hay problemas con el login
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }