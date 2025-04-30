package com.daniel.mynutricoach.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

/**
 * Calcula la edad actual a partir de una fecha de nacimiento en formato "dd/MM/yyyy".
 *
 * Esta función utiliza la API de fechas de Java 8 (LocalDate y Period) para calcular
 * los años completos transcurridos desde la fecha de nacimiento hasta la fecha actual.
 *
 * @param fechaNacimiento Fecha de nacimiento como String en formato "dd/MM/yyyy".
 * @return Edad en años completos. Si la fecha está vacía o mal formateada, devuelve 0.
 */
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