package com.daniel.mynutricoach.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.buttons.NutriBottomNavBar
import com.daniel.mynutricoach.ui.components.forms.CustomOutlinedTextField
import com.daniel.mynutricoach.ui.components.forms.FechaNacimientoTextField
import com.daniel.mynutricoach.ui.components.forms.SexoSelector
import com.daniel.mynutricoach.viewmodel.InitialProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutriEditProfileScreen(
    navController: NavHostController,
    initialProfileViewModel: InitialProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val userData by initialProfileViewModel.userData.collectAsState()
    val saveState by initialProfileViewModel.saveState.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var estatura by remember { mutableStateOf("") }
    var pesoObjetivo by remember { mutableStateOf("") }

    // Rellenar los campos una vez que los datos llegan
    LaunchedEffect(userData) {
        nombre = userData.nombre
        apellidos = userData.apellidos
        telefono = userData.telefono
        fechaNacimiento = userData.fechaNacimiento
        sexo = userData.sexo
        estatura = userData.estatura?.toString() ?: ""
        pesoObjetivo = userData.pesoObjetivo?.toString() ?: ""
    }

    // Validación de formulario
    val isButtonEnabled by remember {
        derivedStateOf {
            nombre.isNotBlank() &&
                    apellidos.isNotBlank() &&
                    telefono.isNotBlank() &&
                    fechaNacimiento.isNotBlank() &&
                    sexo.isNotBlank() &&
                    estatura.toIntOrNull() != null &&
                    pesoObjetivo.toDoubleOrNull() != null
        }
    }

    // Guardar datos y volver al perfil
    LaunchedEffect(saveState) {
        saveState?.onSuccess {
            Toast.makeText(context, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
            navController.navigate("NutriProfile") {
                popUpTo("NutriEditProfile") { inclusive = true }
            }
        }?.onFailure {
            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Editar Perfil", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = { NutriBottomNavBar(navController, "NutriProfile") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            CustomOutlinedTextField(nombre, { nombre = it }, "Nombre")
            Spacer(Modifier.height(16.dp))

            CustomOutlinedTextField(apellidos, { apellidos = it }, "Apellidos")
            Spacer(Modifier.height(16.dp))

            CustomOutlinedTextField(telefono, { telefono = it }, "Teléfono")
            Spacer(Modifier.height(16.dp))

            FechaNacimientoTextField(fechaNacimiento) { fechaNacimiento = it }
            Spacer(Modifier.height(16.dp))

            CustomOutlinedTextField(estatura, { estatura = it }, "Estatura (cm)")
            Spacer(Modifier.height(16.dp))

            CustomOutlinedTextField(pesoObjetivo, { pesoObjetivo = it }, "Peso objetivo (kg)")
            Spacer(Modifier.height(16.dp))

            SexoSelector(sexo) { sexo = it }
            Spacer(Modifier.height(24.dp))

            CustomButton(
                text = "Guardar cambios",
                onClick = {
                    initialProfileViewModel.saveUserData(
                        nombre,
                        apellidos,
                        telefono,
                        fechaNacimiento,
                        sexo,
                        estatura.toInt(),
                        pesoObjetivo.toDouble()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}