package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.navigation.AppScreens
import com.daniel.mynutricoach.ui.components.CustomTextField
import com.daniel.mynutricoach.viewmodel.RegisterViewModel

@Composable
fun Register(navController: NavHostController, registerViewModel: RegisterViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAndConditions by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val registerState by registerViewModel.registerState.collectAsState()

    // Manejo de resultado del registro
    LaunchedEffect(registerState) {
        registerState?.let { result ->
            result.onSuccess { userId ->
                Toast.makeText(context, "Se registró correctamente", Toast.LENGTH_SHORT).show()
                navController.navigate("InitialProfile") {
                    popUpTo("Register") { inclusive = true }
                }
            }.onFailure { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 85.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Crear una cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Crea una cuenta para comenzar",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Correo",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Start)
        )

        // Campo correo con la función CustomTextField
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "ejemplo@ejemplo.com"
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Contraseña",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.Start)
        )

        // Campo contraseña con la función CustomTextField
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Crear contraseña",
            isPassword = true
        )

        Spacer(Modifier.height(12.dp))

        // Campo contraseña con la función CustomTextField
        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = "Confirmar contraseña",
            isPassword = true
        )

        Spacer(Modifier.height(16.dp))

        // Casilla de verificación de términos y condiciones
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = termsAndConditions,
                onCheckedChange = { termsAndConditions = it }
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = "He leído y estoy de acuerdo con los",
                    fontSize = 14.sp
                )

                Row {
                    Text(
                        text = "Términos y Condiciones",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(AppScreens.Terms.ruta)
                        }
                    )

                    Text(
                        text = " y la",
                        fontSize = 14.sp
                    )
                }

                Text(
                    text = "Política de Privacidad",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(AppScreens.Privacy.ruta)
                    }
                )
            }
        }


        Spacer(Modifier.height(16.dp))

        // Botón de registro
        Button(
            onClick = {
                registerViewModel.register(email, password, confirmPassword)
            },
            enabled = termsAndConditions,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Cuenta")
        }
    }
}
