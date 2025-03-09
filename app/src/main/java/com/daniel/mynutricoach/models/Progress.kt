package com.daniel.mynutricoach.models

data class Progress(
    val timestamp: Long = 0L,
    val peso: Float = 0f,
    val masaMuscular: Float = 0f,
    val grasa: Float = 0f
)