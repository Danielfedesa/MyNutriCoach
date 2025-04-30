package com.daniel.mynutricoach.ui.components.dialogues

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daniel.mynutricoach.ui.components.buttons.CustomButton

/**
 * Botón reutilizable para ser usado dentro de diálogos.
 *
 * Este componente es un wrapper de [CustomButton] que ocupa todo el ancho disponible,
 *
 * @param text Texto que se mostrará dentro del botón.
 * @param onClick Acción a ejecutar cuando se presione el botón.
 */
@Composable
fun DialogButton(text: String, onClick: () -> Unit) {
    CustomButton(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    )
}