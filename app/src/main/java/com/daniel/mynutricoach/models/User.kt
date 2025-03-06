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
    val estatura: Int? = null, // Permite valores nulos para no mostrar 0 en la interfaz
    val pesoObjetivo: Double? = null // Permite valores nulos para no mostrar 0 en la interfaz
)