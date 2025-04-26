package com.daniel.mynutricoach.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.CustomButton
import com.daniel.mynutricoach.ui.components.forms.CustomOutlinedTextField
import com.daniel.mynutricoach.ui.components.forms.FechaNacimientoTextField
import com.daniel.mynutricoach.ui.components.forms.SexoSelector
import com.daniel.mynutricoach.viewmodel.InitialProfileViewModel

@Composable
fun InitialProfileScreen(
    navController: NavHostController,
    initialProfileViewModel: InitialProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val userData by initialProfileViewModel.userData.collectAsState()
    val saveState by initialProfileViewModel.saveState.collectAsState()

    var nombre by remember { mutableStateOf(userData.nombre) }
    var apellidos by remember { mutableStateOf(userData.apellidos) }
    var telefono by remember { mutableStateOf(userData.telefono) }
    var fechaNacimiento by remember { mutableStateOf(userData.fechaNacimiento) }
    var sexo by remember { mutableStateOf(userData.sexo) }
    var estatura by remember { mutableStateOf(userData.estatura?.toString() ?: "") }
    var pesoObjetivo by remember { mutableStateOf(userData.pesoObjetivo?.toString() ?: "") }

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

    LaunchedEffect(saveState) {
        saveState?.onSuccess {
            Toast.makeText(context, "Perfil guardado correctamente", Toast.LENGTH_SHORT).show()
            navController.navigate("Progress") {
                popUpTo("InitialProfile") { inclusive = true }
            }
        }?.onFailure {
            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Configura tu perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(nombre, { nombre = it }, "Nombre")
        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(apellidos, { apellidos = it }, "Apellidos")
        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(telefono, { telefono = it }, "Teléfono")
        Spacer(modifier = Modifier.height(16.dp))

        FechaNacimientoTextField(fechaNacimiento) { fechaNacimiento = it }
        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(estatura, { estatura = it }, "Estatura (cm)")
        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedTextField(pesoObjetivo, { pesoObjetivo = it }, "Peso objetivo (kg)")
        Spacer(modifier = Modifier.height(16.dp))

        SexoSelector(sexo) { sexo = it }
        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            text = "Continuar",
            onClick = {
                initialProfileViewModel.saveUserData(
                    nombre, apellidos, telefono, fechaNacimiento, sexo,
                    estatura.toInt(), pesoObjetivo.toDouble()
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled
        )
    }
}