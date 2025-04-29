package com.daniel.mynutricoach.models

/**
 * Representa un registro de progreso físico de un usuario.
 *
 * Este modelo almacena la información relacionada con el estado físico
 * del usuario en un momento específico, incluyendo peso, masa muscular
 * y porcentaje de grasa corporal.
 *
 * @property timestamp Momento en que se registró el progreso, en milisegundos desde la época Unix.
 * @property peso Peso corporal registrado (en kilogramos).
 * @property masaMuscular Masa muscular registrada (en kilogramos).
 * @property grasa Porcentaje de grasa corporal registrado.
 */
data class Progress(
    val timestamp: Long = 0L,
    val peso: Float = 0f,
    val masaMuscular: Float = 0f,
    val grasa: Float = 0f
)