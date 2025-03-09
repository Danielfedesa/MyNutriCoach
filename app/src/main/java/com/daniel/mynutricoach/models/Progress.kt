package com.daniel.mynutricoach.models

data class Progress(
    val timestamp: Long = 0L,
    val peso: Float = 0f,
    val masa_muscular: Float = 0f,
    val grasa: Float = 0f
) {
    // Constructor vacio para Firestore
    constructor() : this(0L, 0f, 0f, 0f)
}