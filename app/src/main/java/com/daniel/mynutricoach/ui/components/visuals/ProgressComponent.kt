package com.daniel.mynutricoach.ui.components.visuals

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable que muestra un valor de progreso (como peso, masa muscular o grasa corporal)
 * junto con un gráfico de evolución histórica.
 *
 * Se utiliza para visualizar datos de salud del usuario de forma clara e intuitiva.
 *
 * @param title Título del parámetro a mostrar (ej. "Peso actual").
 * @param value Valor numérico actual del parámetro.
 * @param unit Unidad de medida del valor (ej. "kg", "%").
 * @param data Lista de pares (timestamp, valor) representando el historial del parámetro.
 */
@Composable
fun ProgressComponent(
    title: String,
    value: Float,
    unit: String,
    data: List<Pair<Long, Float>>
) {
    Text(
        text = "$title: ${"%.1f".format(value)} $unit",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp)
    )
    GraphComponent(data, unit)
    Spacer(modifier = Modifier.height(16.dp))
}