package com.daniel.mynutricoach.models

class Diet(
    val id: String = "",
    val clienteId: String = "",
    val nutricionistaId: String = "",
    val fecha: Long = 0L,
    val descripcion: String = "",
    val alimentos: Map<String, Map<String, List<String>>> = emptyMap()
)