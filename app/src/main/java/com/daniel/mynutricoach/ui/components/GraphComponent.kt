package com.daniel.mynutricoach.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun GraphComponent(values: List<Pair<Long, Float>>, unit: String) {

    // Color de la tarjeta
    val cardColor = Color(0xFFD9EFFC)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start =16.dp, end = 16.dp, bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (values.isNotEmpty()) {
                LineChart(values, unit)
            } else {
                Text("No hay datos disponibles", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun LineChart(values: List<Pair<Long, Float>>, unit: String) {
    val maxValue = (values.maxOfOrNull { it.second } ?: 1f) + 5 // Margen superior
    val minValue = (values.minOfOrNull { it.second } ?: 0f) - 5 // Margen inferior
    val points = values.mapIndexed { index, (timestamp, value) ->
        Pair(index.toFloat(), value)
    }

    val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
    ) {
        val width = size.width
        val height = size.height

        val xStep = width / (points.size - 1).coerceAtLeast(1)
        val yStep = height / (maxValue - minValue)

        // **Líneas de los ejes**
        drawLine(
            color = Color(0xFF8AB1D9), // Azul grisáceo para ejes
            start = Offset(0f, height),
            end = Offset(width, height),
            strokeWidth = 3f
        )

        drawLine(
            color = Color(0xFF8AB1D9),
            start = Offset(0f, 0f),
            end = Offset(0f, height),
            strokeWidth = 3f
        )

        // **Línea de progreso de la gráfica**
        val path = Path().apply {
            points.forEachIndexed { index, (x, y) ->
                val xPos = x * xStep
                val yPos = height - ((y - minValue) * yStep)

                if (index == 0) moveTo(xPos, yPos) else lineTo(xPos, yPos)
            }
        }

        drawPath(
            path,
            color = Color(0xFF38B6FF), // Azul vibrante para la línea
            style = Stroke(width = 8f, cap = StrokeCap.Round)
        )

        // **Puntos de la gráfica**
        points.forEachIndexed { index, (x, y) ->
            val xPos = x * xStep
            val yPos = height - ((y - minValue) * yStep)

            drawCircle(
                color = Color(0xFF0077CC), // Azul oscuro para los puntos
                radius = 8f,
                center = Offset(xPos, yPos)
            )

            // **Etiquetas de valores en el eje Y**
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "${y.roundToInt()} $unit",
                    xPos,
                    yPos - 10,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.parseColor("#444444") // Gris oscuro
                        textSize = 40f
                    }
                )
            }

            // **Etiquetas de fechas en el eje X**
            if (index % 2 == 0) { // Mostrar fecha cada 2 puntos para evitar saturación
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        dateFormat.format(Date(values[index].first)),
                        xPos,
                        height + 40,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#444444") // Gris oscuro
                            textSize = 30f
                        }
                    )
                }
            }
        }
    }
}