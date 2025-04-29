package com.daniel.mynutricoach.models

/**
 * Representa un usuario dentro de la aplicación, ya sea un cliente o un nutricionista.
 *
 * Este modelo almacena la información básica y de perfil de cada usuario registrado,
 * incluyendo datos personales, objetivos y su rol dentro de la plataforma.
 *
 * @property userId Identificador único del usuario en Firebase Authentication.
 * @property email Correo electrónico del usuario.
 * @property role Rol asignado al usuario (por defecto "cliente"; puede ser también "nutricionista").
 * @property nombre Nombre del usuario.
 * @property apellidos Apellidos del usuario.
 * @property telefono Número de teléfono del usuario.
 * @property fechaNacimiento Fecha de nacimiento del usuario (en formato `dd/MM/yyyy`).
 * @property sexo Género del usuario (por ejemplo, "Masculino", "Femenino", etc.).
 * @property estatura Estatura del usuario en centímetros. Puede ser `null` si no está especificado.
 * @property pesoObjetivo Peso objetivo del usuario en kilogramos. Puede ser `null` si no está especificado.
 */
data class User(
    val userId: String = "",
    val email: String = "",
    val role: String = "cliente",
    val nombre: String = "",
    val apellidos: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val sexo: String = "",
    val estatura: Int? = null,
    val pesoObjetivo: Double? = null
)