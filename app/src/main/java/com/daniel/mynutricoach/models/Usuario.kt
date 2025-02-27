package com.daniel.mynutricoach.models

data class Usuario (
    val dni: String,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val email: String,
    val telefono: Int,
    val edad: Int,
    val peso: Double,
    val estatura: Double,
    val sexo: String
)