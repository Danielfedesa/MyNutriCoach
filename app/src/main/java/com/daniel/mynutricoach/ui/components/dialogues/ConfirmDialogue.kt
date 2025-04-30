package com.daniel.mynutricoach.ui.components.dialogues

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daniel.mynutricoach.ui.components.buttons.CustomButton

/**
 * Diálogo visual de éxito con icono y mensaje personalizado.
 *
 * Este componente se muestra para confirmar que una acción se ha realizado correctamente,
 * como guardar datos o completar un proceso. Incluye un icono de éxito, título,
 * mensaje descriptivo y un botón para cerrar el diálogo.
 *
 * @param title Título del diálogo, usualmente algo como "Éxito" o "Operación completada".
 * @param message Texto descriptivo que informa al usuario del resultado exitoso.
 * @param onDismiss Acción que se ejecuta si se cierra el diálogo tocando fuera de él.
 * @param onConfirm Acción que se ejecuta al pulsar el botón de confirmación.
 * @param confirmText Texto del botón de confirmación. Por defecto: "Aceptar".
 */
@Composable
fun SuccessDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "Aceptar"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        text = {
            Text(
                text = message,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    text = confirmText,
                    onClick = onConfirm
                )
            }
        }
    )
}