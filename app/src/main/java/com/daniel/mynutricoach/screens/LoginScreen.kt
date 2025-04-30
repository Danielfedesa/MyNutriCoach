package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.inputs.CustomTextField
import com.daniel.mynutricoach.viewmodel.LoginViewModel

/**
 * Pantalla de inicio de sesión para los usuarios de la aplicación.
 *
 * Permite al usuario autenticarse mediante correo electrónico y contraseña.
 * Si el inicio de sesión es exitoso, redirige a la pantalla correspondiente según el rol:
 * - Clientes: a la pantalla principal de progreso.
 * - Nutricionistas: a la pantalla principal del nutricionista.
 *
 * También incluye:
 * - Validación de credenciales.
 * - Recuperación de contraseña.
 * - Navegación a la pantalla de registro.
 *
 * @param navController Controlador de navegación para cambiar entre pantallas.
 * @param loginViewModel ViewModel que gestiona el proceso de autenticación.
 */
@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isAuthenticated by loginViewModel.isAuthenticated.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()

    // Verifica si el usuario ya está autenticado
    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated == true) {
            val destination =
                if (loginViewModel.userRole.value == "nutricionista") "NutriHome" else "Progress"
            navController.navigate(destination) {
                popUpTo("Login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                .weight(0.7f)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bienvenido",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(value = email, onValueChange = { email = it }, label = "Correo")
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color.Blue,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable { loginViewModel.resetPassword(email) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            CustomButton(
                text = "Iniciar sesión",
                onClick = {
                    loginViewModel.signIn(email, password) { route ->
                        navController.navigate(route) {
                            popUpTo("Login") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row {
                Text(text = "¿No tienes una cuenta? ", fontSize = 16.sp, color = Color.DarkGray)
                Text(
                    text = "Regístrate ahora",
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("Register")
                    }
                )
            }
        }
    }

    errorMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}