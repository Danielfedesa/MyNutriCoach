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

// Button para seleccionar un horario de cita en la pantalla de NutriAppointments
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