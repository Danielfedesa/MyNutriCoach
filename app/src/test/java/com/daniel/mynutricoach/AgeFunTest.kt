package com.daniel.mynutricoach

import com.daniel.mynutricoach.utils.obtenerEdad
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AgeFuncTest {

    @Test
    fun testFechaValida() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val hoy = LocalDate.now()
        val nacimiento = hoy.minusYears(25)
        val fecha = nacimiento.format(formatter)
        val edad = obtenerEdad(fecha)
        assertEquals(25, edad)
    }

    @Test
    fun testFechaVacia() {
        val edad = obtenerEdad("")
        assertEquals(0, edad)
    }

    @Test
    fun testFechaInvalida() {
        val edad = obtenerEdad("fecha-mal")
        assertEquals(0, edad)
    }
}
