package com.daniel.mynutricoach.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun obtenerEdad(fechaNacimiento: String): Int {
    if (fechaNacimiento.isBlank()) {
        return 0 // O cualquier valor que tenga sentido si no hay fecha
    }

    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val nacimiento = LocalDate.parse(fechaNacimiento, formatter)
        val hoy = LocalDate.now()
        Period.between(nacimiento, hoy).years
    } catch (e: Exception) {
        0 // Si falla el parseo, devolvemos 0
    }
}