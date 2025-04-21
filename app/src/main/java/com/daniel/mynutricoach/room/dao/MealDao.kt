package com.daniel.mynutricoach.room.dao

import androidx.room.*
import com.daniel.mynutricoach.room.entities.MealEntity

@Dao
interface MealDao {
    @Query("SELECT * FROM meals WHERE dia = :dia")
    suspend fun getMealsForDay(dia: String): List<MealEntity>

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<MealEntity>)

    @Query("DELETE FROM meals")
    suspend fun clearAllMeals()
}