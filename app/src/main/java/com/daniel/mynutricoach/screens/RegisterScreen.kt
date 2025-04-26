package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.navigation.AppScreens
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAndConditions by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val registerState by registerViewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        registerState?.let { result ->
            result.onSuccess {
                Toast.makeText(context, "Se registró correctamente", Toast.LENGTH_SHORT).show()
                navController.navigate("InitialProfile") {
                    popUpTo("Register") { inclusive = true }
                }
            }.onFailure { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "ejemplo@ejemplo.com"
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

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Crear contraseña",
                isPassword = true
            )

            Spacer(Modifier.height(12.dp))

            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar contraseña",
                isPassword = true
            )

            Spacer(Modifier.height(16.dp))

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

            Spacer(Modifier.height(24.dp))

            CustomButton(
                text = "Crear Cuenta",
                onClick = { registerViewModel.register(email, password, confirmPassword) },
                enabled = termsAndConditions,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
