package com.daniel.mynutricoach.ui.components.dialogues

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daniel.mynutricoach.ui.components.buttons.CustomButton

/**
 * Diálogo de alerta personalizado reutilizable con botones confirmación y cancelación.
 *
 * Permite personalizar el título, mensaje y acciones, y mostrar opcionalmente
 * un botón de cancelación con texto personalizado.
 *
 * @param title Composable que representa el título del diálogo.
 * @param message Composable que representa el contenido del mensaje del diálogo.
 * @param confirmText Texto a mostrar en el botón de confirmación.
 * @param onConfirm Acción que se ejecuta al pulsar el botón de confirmación.
 * @param onDismiss Acción que se ejecuta al cerrar el diálogo (ya sea cancelación o toque fuera).
 * @param dismissText Texto opcional para mostrar un botón de cancelación. Si es null, no se muestra.
 */
@Composable
fun CustomAlertDialog(
    title: @Composable () -> Unit,
    message: @Composable () -> Unit,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dismissText: String? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = title,
        text = message,
        confirmButton = {
            Column(
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomButton(
                    text = confirmText,
                    onClick = onConfirm,
                    modifier = Modifier.width(220.dp)
                )
            }
        },
        dismissButton = dismissText?.let {
            {
                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomButton(
                        text = it,
                        onClick = onDismiss,
                        containerColor = androidx.compose.ui.graphics.Color.White,
                        contentColor = androidx.compose.ui.graphics.Color.Black,
                        modifier = Modifier.width(220.dp)
                    )
                }
            }
        }
    )
}
