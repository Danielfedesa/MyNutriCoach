package com.daniel.mynutricoach.ui.components.dialogues

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daniel.mynutricoach.ui.components.buttons.CustomButton

@Composable
fun DialogButton(text: String, onClick: () -> Unit) {
    CustomButton(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    )
}