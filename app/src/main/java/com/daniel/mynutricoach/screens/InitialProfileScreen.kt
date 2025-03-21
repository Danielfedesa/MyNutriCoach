package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.daniel.mynutricoach.ui.components.CustomOutlinedTextField
import com.daniel.mynutricoach.ui.components.FechaNacimientoTextField
import com.daniel.mynutricoach.ui.components.SexoSelector
import com.daniel.mynutricoach.viewmodel.InitialProfileViewModel


@Composable
fun InitialProfileComp(navController: NavHostController, initialProfileViewModel: InitialProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val userData by initialProfileViewModel.userData.collectAsState()
    val saveState by initialProfileViewModel.saveState.collectAsState()

    var nombre by remember { mutableStateOf(userData.nombre) }
    var apellidos by remember { mutableStateOf(userData.apellidos) }
    var telefono by remember { mutableStateOf(userData.telefono) }
    var fechaNacimiento by remember { mutableStateOf(userData.fechaNacimiento) }
    var sexo by remember { mutableStateOf(userData.sexo) }
    var estatura by remember { mutableStateOf(userData.estatura?.toString() ?: "") } // Si es null, muestra ""
    var pesoObjetivo by remember { mutableStateOf(userData.pesoObjetivo?.toString() ?: "") } // Si es null, muestra ""

    // Estado que controla si el botón debe estar habilitado
    val isButtonEnabled by remember {
        derivedStateOf {
            nombre.isNotBlank() &&
                    apellidos.isNotBlank() &&
                    telefono.isNotBlank() &&
                    fechaNacimiento.isNotBlank() &&
                    sexo.isNotBlank() &&
                    estatura.isNotBlank() &&
                    pesoObjetivo.isNotBlank() &&
                    estatura.toIntOrNull() != null &&
                    pesoObjetivo.toDoubleOrNull() != null
        }
    }

    LaunchedEffect(saveState) {
        saveState?.let { result ->
            result.onSuccess {
                Toast.makeText(context, "Perfil guardado correctamente", Toast.LENGTH_SHORT).show()
                navController.navigate("Progress") {
                    popUpTo("InitialProfile") { inclusive = true }
                }
            }.onFailure { error ->
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Configura tu perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = "Nombre"
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = "Apellidos"
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = "Teléfono"
        )

        Spacer(Modifier.height(16.dp))

        FechaNacimientoTextField(
            selectedDate = fechaNacimiento,
            onDateSelected = { fechaNacimiento = it }
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = estatura,
            onValueChange = { estatura = it },
            label = "Estatura (cm)"
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = pesoObjetivo,
            onValueChange = { pesoObjetivo = it },
            label = "Peso objetivo (kg)"
        )

        Spacer(Modifier.height(16.dp))

        SexoSelector(
            selectedSexo = sexo,
            onSexoSelected = { sexo = it }
        )

        Spacer(Modifier.height(50.dp))

        // Botón solo habilitado si todos los campos están completos
        Button(
            onClick = {
                initialProfileViewModel.saveUserData(
                    nombre, apellidos, telefono, fechaNacimiento, sexo, estatura.toInt(), pesoObjetivo.toDouble()
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled // 🔹 Controla si el botón está habilitado o no
        ) {
            Text("Continuar")
        }
    }
}