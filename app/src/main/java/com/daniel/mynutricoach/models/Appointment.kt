package com.daniel.mynutricoach.models

data class Appointment (
    val id: String = "",
    val clienteId: String = "",
    val nutricionistaId: String = "",
    val fecha: Long = 0L,
    val hora: String = "",
    val motivo: String = "",
    val notas: String = ""
)