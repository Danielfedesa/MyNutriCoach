package com.daniel.mynutricoach

import com.daniel.mynutricoach.utils.obtenerEdad
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Clase de prueba para la función [obtenerEdad].
 *
 * Esta clase contiene pruebas unitarias que verifican el correcto funcionamiento de la función
 * al calcular la edad a partir de una fecha de nacimiento dada en formato "dd/MM/yyyy".
 */
class AgeFuncTest {

    /**
     * Prueba unitaria para verificar el cálculo de la edad a partir de una fecha válida.
     *
     * Se crea una fecha de nacimiento 25 años atrás y se verifica que la edad calculada
     * sea efectivamente 25 años.
     */
    @Test
    fun testFechaValida() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val hoy = LocalDate.now()
        val nacimiento = hoy.minusYears(25)
        val fecha = nacimiento.format(formatter)
        val edad = obtenerEdad(fecha)
        assertEquals(25, edad)
    }

    /**
     * Prueba unitaria para verificar el cálculo de la edad a partir de una fecha futura.
     *
     * Se crea una fecha de nacimiento en el futuro y se verifica que la edad calculada
     * sea 0 años.
     */
    @Test
    fun testFechaVacia() {
        val edad = obtenerEdad("")
        assertEquals(0, edad)
    }

    /**
     * Prueba unitaria para verificar el cálculo de la edad a partir de una fecha inválida.
     *
     * Se proporciona una fecha en un formato incorrecto y se verifica que la edad calculada
     * sea 0 años.
     */
    @Test
    fun testFechaInvalida() {
        val edad = obtenerEdad("fecha-mal")
        assertEquals(0, edad)
    }
}
