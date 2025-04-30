package com.daniel.mynutricoach.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Muestra un título de sección con un icono a la izquierda.
 *
 * Usado típicamente para dividir bloques de contenido en una interfaz de usuario.
 *
 * @param title Texto que se mostrará como título.
 * @param icon Icono que acompaña al título.
 */
@Composable
fun SectionTitle(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}

/**
 * Tarjeta simple utilizada para mostrar información básica de una cita.
 *
 * Puede incluir el nombre del cliente, la fecha y la hora de la cita.
 * Se adapta a distintos contextos mostrando sólo lo necesario.
 *
 * @param cliente Nombre completo del cliente (opcional).
 * @param fecha Fecha de la cita en formato de texto (opcional).
 * @param hora Hora de la cita (requerido).
 */
@Composable
fun AppointmentSimpleCard(cliente: String? = null, fecha: String? = null, hora: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            cliente?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            fecha?.let {
                Text(
                    text = "Fecha: $it",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp
                    )
                )
            }
            Text(
                text = "Hora: $hora",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp
                )
            )
        }
    }
}