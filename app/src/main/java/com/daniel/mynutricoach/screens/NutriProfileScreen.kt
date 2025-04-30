package com.daniel.mynutricoach.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.NutriBottomNavBar
import com.daniel.mynutricoach.ui.components.dialogues.CloseSession
import com.daniel.mynutricoach.ui.components.visuals.ProfileOption
import com.daniel.mynutricoach.viewmodel.ProfileViewModel

/**
 * Pantalla de perfil para el usuario con rol de nutricionista.
 *
 * Muestra:
 * - Nombre del usuario.
 * - Acceso a opciones de configuración personal como datos, notificaciones e idioma.
 * - Enlaces a secciones legales (privacidad y términos).
 * - Botón para cerrar sesión con confirmación.
 *
 * @param navController Controlador de navegación para redirigir entre pantallas.
 * @param profileViewModel ViewModel que maneja los datos del perfil y la lógica de logout.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NutriProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userName by profileViewModel.userName.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    if (showDialog) {
        CloseSession(
            title = "Cerrar sesión",
            message = "¿Estás seguro de que quieres cerrar sesión?",
            onConfirm = {
                showDialog = false
                profileViewModel.logout(navController)
            },
            onDismiss = { showDialog = false },
            confirmText = "Cerrar sesión",
            dismissText = "Cancelar"
        )
    }

    Scaffold(
        topBar = {},
        bottomBar = { NutriBottomNavBar(navController, "NutriProfile") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Avatar",
                    tint = Color.LightGray,
                    modifier = Modifier.size(96.dp)
                )
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar",
                    tint = Color.Blue,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                )
            }

            Text(
                text = userName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )

            ProfileOption("Datos personales") {
                navController.navigate("NutriEditProfile")
            }
            ProfileOption("Notificaciones") {
                navController.navigate("editNotifications")
            }
            ProfileOption("Idioma") {
                navController.navigate("editLanguage")
            }
            ProfileOption("Política de privacidad") {
                navController.navigate("Privacy")
            }
            ProfileOption("Términos y condiciones") {
                navController.navigate("Terms")
            }
            ProfileOption("Obtener ayuda") {
                navController.navigate("Help")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D4D))
            ) {
                Text(
                    "Cerrar sesión",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}