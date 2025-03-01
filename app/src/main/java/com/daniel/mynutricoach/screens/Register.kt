package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var role by remember { mutableStateOf("cliente") }
    var termsAndConditions by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val registerState by registerViewModel.registerState.collectAsState()

    // Manejo de resultado del registro
    LaunchedEffect(registerState) {
        registerState?.let { result ->
            result.onSuccess { userId ->
                Toast.makeText(context, "Registro exitoso. UID: $userId", Toast.LENGTH_SHORT).show()
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
            modifier = Modifier
                .align(Alignment.Start)
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

            // Texto con enlaces clickeables
            val annotatedString = buildAnnotatedString {
                append("He leído y estoy de acuerdo con los ")

                pushStringAnnotation(tag = "terms", annotation = "terms")
                withStyle(style = SpanStyle(color = Color.Blue)) { append("Términos y Condiciones") }
                pop()

                append(" y la ")

                pushStringAnnotation(tag = "privacy", annotation = "privacy")
                withStyle(style = SpanStyle(color = Color.Blue)) { append("Política de Privacidad") }
                pop()
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "terms",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            navController.navigate(AppScreens.Terms.ruta)
                        }

                    annotatedString.getStringAnnotations(
                        tag = "privacy",
                        start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let {
                            navController.navigate(AppScreens.Privacy.ruta)
                        }
                }
            )
        }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    registerViewModel.register(email, password, confirmPassword, role)
                },
                enabled = termsAndConditions,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Cuenta")
            }
        }
    }
