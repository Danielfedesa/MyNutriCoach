package com.daniel.mynutricoach.models

data class Notification(
    val id: String = "",
    val usuarioId: String = "",
    val titulo: String = "",
    val mensaje: String = "",
    val tipo: String = "",
    val leido: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)