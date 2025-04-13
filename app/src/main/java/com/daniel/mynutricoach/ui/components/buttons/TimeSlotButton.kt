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

// Button para seleccionar un horario de cita en la pantalla de NutriAppointments
@Composable
fun TimeSlotButton(time: String, isTaken: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !isTaken,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isTaken) Color.LightGray else MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .width(100.dp)
            .height(50.dp)
    ) {
        Text(time)
    }
}