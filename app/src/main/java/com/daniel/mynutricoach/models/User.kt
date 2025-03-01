package com.daniel.mynutricoach.models

data class User(
    val userId: String = "",
    val email: String = "",
    val role: String = "cliente", // Cliente por defecto, también puede ser "nutricionista"
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val sexo: String = "",
    val estatura: Int = 0,
    val pesoObjetivo: Double = 0.0
)