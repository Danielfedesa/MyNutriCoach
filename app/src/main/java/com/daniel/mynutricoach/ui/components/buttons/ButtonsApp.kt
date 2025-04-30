package com.daniel.mynutricoach.ui.components.buttons

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Botón personalizado reutilizable con estilo predeterminado para la aplicación.
 *
 * Permite establecer el texto, acción al pulsar, colores y estado de habilitación.
 *
 * @param text Texto que se muestra en el botón.
 * @param onClick Acción que se ejecuta cuando el botón es pulsado.
 * @param modifier [Modifier] para aplicar estilos externos (como padding o alignment).
 * @param enabled Indica si el botón está habilitado o no.
 * @param containerColor Color de fondo del botón (por defecto, color primario del tema).
 * @param contentColor Color del texto dentro del botón (por defecto, color sobre primario).
 */
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.White
        ),
        modifier = Modifier
            .width(220.dp)
            .then(modifier),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

