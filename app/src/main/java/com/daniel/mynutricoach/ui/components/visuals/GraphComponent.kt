package com.daniel.mynutricoach.ui.components.visuals

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt
import androidx.core.graphics.toColorInt

@Composable
fun GraphComponent(values: List<Pair<Long, Float>>, unit: String) {
    val cardColor = Color(0xFFE3F2FD) // Fondo azul clarito
    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (values.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState) // Scroll horizontal
                ) {
                    LineChart(values, unit, scrollState)
                }
            } else {
                Text("No hay datos disponibles", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun LineChart(values: List<Pair<Long, Float>>, unit: String, scrollState: ScrollState) {
    val maxValue = (values.maxOfOrNull { it.second } ?: 1f) + 5
    val minValue = (values.minOfOrNull { it.second } ?: 0f) - 5
    val points = values.mapIndexed { index, (timestamp, value) -> index.toFloat() to value }
    val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())

    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 5000)
        )
    }
    // Acompaña el scroll con la animación
    LaunchedEffect(animationProgress.value) {
        val maxScroll = scrollState.maxValue
        scrollState.scrollTo((animationProgress.value * maxScroll).toInt())
    }

    Canvas(
        modifier = Modifier
            .height(200.dp)
            .width((values.size.coerceAtLeast(3) * 50 + 50).dp)
            .padding(horizontal = 16.dp)
    ) {
        val width = size.width
        val height = size.height

        val xStep = (width - 50f) / (points.size - 1).coerceAtLeast(1)
        val yStep = height / (maxValue - minValue)

        // Ejes
        drawLine(Color(0xFF1976D2), Offset(0f, 0f), Offset(0f, height), 4f)
        drawLine(Color(0xFF1976D2), Offset(0f, height), Offset(width - 50f, height), 4f)

        // Path parcial FLUIDO
        val fullPath = Path()
        points.forEachIndexed { index, (x, y) ->
            val xPos = x * xStep
            val yPos = height - ((y - minValue) * yStep)
            if (index == 0) fullPath.moveTo(xPos, yPos) else fullPath.lineTo(xPos, yPos)
        }

        val totalLength = points.size - 1
        val currentLength = totalLength * animationProgress.value

        val animatedPath = Path()
        points.forEachIndexed { index, (x, y) ->
            val xPos = x * xStep
            val yPos = height - ((y - minValue) * yStep)

            if (index.toFloat() <= currentLength) {
                if (index == 0) animatedPath.moveTo(xPos, yPos) else animatedPath.lineTo(xPos, yPos)
            } else if (index.toFloat() > currentLength && index > 0) {
                // Interpolamos entre el último punto y el siguiente
                val progressBetweenPoints = currentLength - (index - 1)
                val prevX = points[index - 1].first * xStep
                val prevY = height - ((points[index - 1].second - minValue) * yStep)

                val interpolatedX = prevX + (xPos - prevX) * progressBetweenPoints
                val interpolatedY = prevY + (yPos - prevY) * progressBetweenPoints

                animatedPath.lineTo(interpolatedX, interpolatedY)
                return@forEachIndexed
            }
        }

        drawPath(
            animatedPath,
            color = Color(0xFF2196F3),
            style = Stroke(width = 6f, cap = StrokeCap.Round)
        )

        // Puntos y etiquetas
        points.forEachIndexed { index, (x, y) ->
            val xPos = x * xStep
            val yPos = height - ((y - minValue) * yStep)

            if (index.toFloat() <= currentLength) {
                drawCircle(Color(0xFF1565C0), radius = 8f, center = Offset(xPos, yPos))

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${y.roundToInt()} $unit",
                        xPos,
                        yPos - 20,
                        android.graphics.Paint().apply {
                            color = "#555555".toColorInt()
                            textSize = 42f
                        }
                    )
                }

                if (index % 2 == 0) {
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            dateFormat.format(Date(values[index].first)),
                            xPos,
                            height + 40,
                            android.graphics.Paint().apply {
                                color = "#555555".toColorInt()
                                textSize = 36f
                            }
                        )
                    }
                }
            }
        }
    }
}
