package com.daniel.mynutricoach.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dia: String,
    val tipo: String,
    val alimentos: String // Almacena los alimentos como texto separado por "|"
)