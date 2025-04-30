package com.daniel.mynutricoach.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Botón utilizado para seleccionar un horario disponible para una cita.
 *
 * Este componente se usa en la pantalla de añadir citas por parte del nutricionista.
 * El botón estará desactivado si el horario ya está reservado.
 *
 * @param time Texto que representa el horario (formato "HH:mm").
 * @param isTaken Booleano que indica si el horario ya está ocupado.
 * @param onClick Acción a ejecutar al pulsar el botón (solo si está habilitado).
 */
@Composable
fun TimeSlotButton(time: String, isTaken: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !isTaken,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isTaken) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .width(100.dp)
            .height(70.dp)
    ) {
        Text(
            text = time,
            fontSize = 20.sp
        )
    }
}