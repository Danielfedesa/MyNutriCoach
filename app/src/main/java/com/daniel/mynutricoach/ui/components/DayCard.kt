package com.daniel.mynutricoach.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCard(day: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(75.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                    fontSize = 12.sp
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
                    fontSize = 12.sp
                )
            )
        }
    }
}