package com.daniel.mynutricoach.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun obtenerEdad(fechaNacimiento: String): Int {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val nacimiento = LocalDate.parse(fechaNacimiento, formatter)
    val hoy = LocalDate.now()
    return Period.between(nacimiento, hoy).years
}