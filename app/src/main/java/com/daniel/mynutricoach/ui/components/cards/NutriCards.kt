package com.daniel.mynutricoach.ui.components.cards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daniel.mynutricoach.models.Appointment
import com.daniel.mynutricoach.models.User
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Muestra una tarjeta con la información básica de un cliente.
 *
 * Incluye nombre completo e email. Al hacer clic, ejecuta la acción proporcionada.
 *
 * @param cliente Objeto [User] con los datos del cliente.
 * @param onClick Acción a ejecutar al pulsar sobre la tarjeta.
 */
@Composable
fun ClienteCard(cliente: User, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "${cliente.nombre} ${cliente.apellidos}",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Text(cliente.email, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

/**
 * Tarjeta utilizada en la pantalla de citas del nutricionista.
 *
 * Muestra los datos de la cita con botones para finalizar o cancelar la misma.
 *
 * @param appointment Cita actual representada por un objeto [Appointment].
 * @param onFinalize Acción a ejecutar al pulsar el botón de finalizar.
 * @param onCancel Acción a ejecutar al pulsar el botón de cancelar.
 */
@Composable
fun NutriAppointmentCard(
    appointment: Appointment,
    onFinalize: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${appointment.clienteNombre} ${appointment.clienteApellido}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Fecha",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = appointment.fecha)

                    Spacer(Modifier.width(12.dp))

                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Hora",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = appointment.hora)
                }

                // Botones pequeños a la derecha
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(color = Color(0xFF4CAF50), shape = CircleShape)
                            .clickable { onFinalize() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Finalizar",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(color = Color(0xFFF44336), shape = CircleShape)
                            .clickable { onCancel() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cancelar",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Muestra una tarjeta visual para seleccionar un día en el calendario.
 *
 * Se utiliza principalmente para seleccionar fechas en el registro de citas.
 *
 * @param day Día a representar ([LocalDate]).
 * @param isSelected Indica si el día está seleccionado.
 * @param onClick Acción a ejecutar al pulsar la tarjeta.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCard(day: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(75.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es")),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = textColor,
                    fontSize = 16.sp
                )
            )
            Text(
                text = day.dayOfMonth.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = textColor,
                    fontSize = 24.sp
                )
            )
            Text(
                text = day.month.getDisplayName(TextStyle.SHORT, Locale("es")),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = textColor,
                    fontSize = 16.sp
                )
            )
        }
    }
}