package com.daniel.mynutricoach.models

data class User (
    val userId: String = "",
    val email: String = "",
    val role: String = "cliente" // Por defecto el rol es cliente, pero puede ser nutricionista
)