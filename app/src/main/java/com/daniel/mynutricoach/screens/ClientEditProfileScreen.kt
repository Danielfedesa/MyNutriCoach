package com.daniel.mynutricoach.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.BottomNavBar
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.forms.CustomOutlinedTextField
import com.daniel.mynutricoach.ui.components.forms.FechaNacimientoTextField
import com.daniel.mynutricoach.ui.components.forms.SexoSelector
import com.daniel.mynutricoach.viewmodel.InitialProfileViewModel

/**
 * Pantalla de edición del perfil personal del cliente.
 *
 * Permite al usuario modificar sus datos personales, como nombre, apellidos, teléfono,
 * fecha de nacimiento, sexo, estatura y peso objetivo. Esta información se guarda en Firebase
 * a través del [InitialProfileViewModel].
 *
 * Comportamiento destacado:
 * - Carga los datos del usuario al iniciarse con [LaunchedEffect].
 * - Valida los campos antes de permitir guardar cambios.
 * - Muestra un Toast informando del éxito o error al guardar.
 * - Redirige al perfil al completar el guardado correctamente.
 *
 * Componentes utilizados:
 * - Campos de entrada personalizados ([CustomOutlinedTextField], [FechaNacimientoTextField], [SexoSelector]).
 * - Botón de acción ([CustomButton]).
 * - Barra superior personalizada con botón de retroceso.
 * - Barra de navegación inferior con "Profile" activo.
 *
 * @param navController Controlador de navegación para redirigir y volver atrás.
 * @param initialProfileViewModel ViewModel encargado de manejar la lógica de guardado de datos.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientEditProfileScreen(
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

    val isButtonEnabled by remember {
        derivedStateOf {
            nombre.isNotBlank() && apellidos.isNotBlank() && telefono.isNotBlank() &&
                    fechaNacimiento.isNotBlank() && sexo.isNotBlank() &&
                    estatura.toIntOrNull() != null && pesoObjetivo.toDoubleOrNull() != null
        }
    }

    LaunchedEffect(userData) {
        nombre = userData.nombre
        apellidos = userData.apellidos
        telefono = userData.telefono
        fechaNacimiento = userData.fechaNacimiento
        sexo = userData.sexo
        estatura = userData.estatura?.toString() ?: ""
        pesoObjetivo = userData.pesoObjetivo?.toString() ?: ""
    }

    LaunchedEffect(saveState) {
        saveState?.onSuccess {
            Toast.makeText(context, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
            navController.navigate("Profile") {
                popUpTo("EditProfile") { inclusive = true }
            }
        }?.onFailure {
            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Editar datos personales",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = { BottomNavBar(navController, "Profile") }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 32.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
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
                        nombre, apellidos, telefono, fechaNacimiento, sexo,
                        estatura.toInt(), pesoObjetivo.toDouble()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}
