package com.daniel.mynutricoach.ui.components.visuals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Componente visual que muestra un dato clave en formato de caja con título y valor.
 *
 * Utilizado en pantallas como `ProfileScreen` para mostrar información como peso actual,
 * objetivo o edad del usuario.
 *
 * @param titulo Etiqueta descriptiva del dato (por ejemplo: "Edad", "Peso actual").
 * @param valor Valor que se quiere mostrar (por ejemplo: "72 Kg", "30 años").
 */
@Composable
fun InfoBox(titulo: String, valor: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = valor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = titulo,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}