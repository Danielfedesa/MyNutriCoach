package com.daniel.mynutricoach.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Selector de sexo basado en botones de radio.
 *
 * Muestra las opciones "Masculino", "Femenino" y "Otro", permitiendo al usuario seleccionar una.
 *
 * @param selectedSexo Valor actual del sexo seleccionado (ej. "Masculino").
 * @param onSexoSelected Función que se llama cuando se selecciona una nueva opción.
 */
@Composable
fun SexoSelector(selectedSexo: String, onSexoSelected: (String) -> Unit) {
    val opcionesAbreviadas = listOf("Masc", "Feme", "Otro")
    val opcionesCompletas = mapOf("Masc" to "Masculino", "Feme" to "Femenino", "Otro" to "Otro")

    var selectedOption by remember(selectedSexo) {
        mutableStateOf(
            opcionesCompletas.entries.find { it.value == selectedSexo }?.key ?: "Otro"
        )
    }

    Column {
        Text(
            "Sexo",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            opcionesAbreviadas.forEach { abreviado ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    RadioButton(
                        selected = abreviado == selectedOption,
                        onClick = {
                            selectedOption = abreviado
                            onSexoSelected(opcionesCompletas[abreviado] ?: "Otro")
                        }
                    )
                    Text(text = abreviado, modifier = Modifier.padding(start = 2.dp))
                }
            }
        }
    }
}

/**
 * Campo de texto para ingresar una fecha de nacimiento con formato automático `DD/MM/AAAA`.
 *
 * Formatea el input conforme el usuario escribe, añadiendo automáticamente las barras.
 *
 * @param selectedDate Fecha actual seleccionada (formato `DD/MM/AAAA`).
 * @param onDateSelected Callback que se ejecuta cuando la fecha está completa.
 */
@Composable
fun FechaNacimientoTextField(selectedDate: String, onDateSelected: (String) -> Unit) {
    var fecha by remember(selectedDate) { mutableStateOf(selectedDate) }

    OutlinedTextField(
        value = fecha,
        onValueChange = { newValue ->
            fecha = formatFecha(newValue) // Formatear la entrada del usuario
            if (fecha.length == 10) {
                onDateSelected(fecha) // Enviar la fecha cuando esté completa
            }
        },
        label = { Text("Fecha de Nacimiento (DD/MM/AAAA)") },
        placeholder = { Text("DD/MM/AAAA") },
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * Formatea una cadena de texto para que tenga el formato `DD/MM/AAAA`.
 *
 * @param input Cadena de texto a formatear.
 * @return Cadena formateada en `DD/MM/AAAA`.
 */
fun formatFecha(input: String): String {
    val digits = input.filter { it.isDigit() } // Extrae solo los números
    val builder = StringBuilder()

    for (i in digits.indices) {
        builder.append(digits[i])
        if (i == 1 || i == 3) { // Añade `/` después del día y el mes
            builder.append("/")
        }
    }

    return builder.toString().take(10) // Limita la longitud máxima a `DD/MM/AAAA`
}

/**
 * Campo de texto con estilo `OutlinedTextField`, esquinas redondeadas y teclado personalizado.
 *
 * @param value Valor actual del campo.
 * @param onValueChange Callback que se ejecuta al cambiar el texto.
 * @param label Etiqueta que describe el campo.
 * @param keyboardType Tipo de teclado a mostrar (por defecto es [KeyboardType.Text]).
 */
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        shape = RoundedCornerShape(12.dp)
    )
}