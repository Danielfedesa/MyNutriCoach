package com.daniel.mynutricoach.ui.components.dialogues

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daniel.mynutricoach.ui.components.buttons.CustomButton

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
