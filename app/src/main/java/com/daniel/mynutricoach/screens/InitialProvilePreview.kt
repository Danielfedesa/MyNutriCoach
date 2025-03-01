package com.daniel.mynutricoach.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daniel.mynutricoach.ui.components.CustomOutlinedTextField

/*
@Composable
fun InitialProfilePreview() {
    // Simulación de datos en lugar del ViewModel real
    val fakeUserData = mapOf(
        "email" to "test@demo.com",
        "nombre" to "Usuario Demo"
    )

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

        Text("Bienvenido, ${fakeUserData["email"] ?: "Usuario"}")

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = "Nombre",
            onValueChange = { /* No hace nada en la vista previa */ },
            label = "Nombre"
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = "Apellidos",
            onValueChange = { /* No hace nada en la vista previa */ },
            label = "Apellidos"
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = "Teléfono",
            onValueChange = { /* No hace nada en la vista previa */ },
            label = "Teléfono"
        )

        Spacer(Modifier.height(16.dp))

        DatePickerComponent()

        Spacer(Modifier.height(16.dp))

        SexoDropdown()

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = "Estatura (cm)",
            onValueChange = { /* No hace nada en la vista previa */ },
            label = "Estatura (cm)"
        )

        Spacer(Modifier.height(16.dp))

        CustomOutlinedTextField(
            value = "Peso objetivo (kg)",
            onValueChange = { /* No hace nada en la vista previa */ },
            label = "Peso objetivo (kg)"
        )

        Spacer(Modifier.height(50.dp))

        Button(
            onClick = { /* No hace nada en la vista previa */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}

// Vista previa funcional sin tocar el código original
@Preview(showBackground = true, name = "Vista Previa")
@Composable
fun ShowInitialProfilePreview() {
    InitialProfilePreview()
}
*/