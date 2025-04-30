package com.daniel.mynutricoach.ui.components.dialogues

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
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
 * Diálogo de confirmación para cerrar sesión.
 *
 * Muestra un mensaje de advertencia al usuario y ofrece dos opciones:
 * aceptar para cerrar sesión o cancelar para permanecer en la aplicación.
 *
 * @param title Título del diálogo, normalmente algo como "Cerrar sesión".
 * @param message Mensaje explicativo o pregunta al usuario.
 * @param onDismiss Acción que se ejecuta si el usuario cancela.
 * @param onConfirm Acción que se ejecuta si el usuario confirma.
 * @param confirmText Texto del botón de confirmación. Por defecto: "Aceptar".
 * @param dismissText Texto del botón de cancelación. Por defecto: "Cancelar".
 */
@Composable
fun CloseSession(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
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
                    onClick = onConfirm,
                    modifier = Modifier.width(220.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    text = dismissText,
                    onClick = onDismiss,
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    modifier = Modifier.width(220.dp)
                )
            }
        }
    )
}
