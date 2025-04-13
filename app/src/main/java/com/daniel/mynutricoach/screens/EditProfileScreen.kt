package com.daniel.mynutricoach.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.daniel.mynutricoach.ui.components.buttons.BottomNavBar
import com.daniel.mynutricoach.ui.components.forms.CustomOutlinedTextField
import com.daniel.mynutricoach.ui.components.forms.FechaNacimientoTextField
import com.daniel.mynutricoach.ui.components.forms.SexoSelector
import com.daniel.mynutricoach.viewmodel.InitialProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileComp(navController: NavHostController, initialProfileViewModel: InitialProfileViewModel = viewModel()) {

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

    // Guardar datos y volver a perfil
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = {},
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        bottomBar = { BottomNavBar(navController, "Profile") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Editar datos personales",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(Modifier.height(16.dp))

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

            Button(
                onClick = {
                    initialProfileViewModel.saveUserData(
                        nombre, apellidos, telefono, fechaNacimiento, sexo,
                        estatura.toInt(), pesoObjetivo.toDouble()
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            ) {
                Text("Guardar cambios")
            }
        }
    }
}