package com.daniel.mynutricoach.repository

import com.daniel.mynutricoach.room.AppDatabase

import android.content.Context
import com.daniel.mynutricoach.models.Meal
import com.daniel.mynutricoach.room.entities.MealEntity

class RoomDietRepository(context: Context) {
    private val mealDao = AppDatabase.getInstance(context).mealDao()

    suspend fun saveDietaToLocal(dieta: Map<String, List<Meal>>) {
        mealDao.clearAllMeals()
        val mealsToInsert = dieta.flatMap { (dia, comidas) ->
            comidas.map {
                MealEntity(dia = dia, tipo = it.tipo, alimentos = it.alimentos.joinToString("|"))
            }
        }
        mealDao.insertMeals(mealsToInsert)
    }

    suspend fun loadDietaFromLocal(): Map<String, List<Meal>> {
        return mealDao.getAllMeals().groupBy { it.dia }.mapValues { entry ->
            entry.value.map {
                Meal(it.tipo, it.alimentos.split("|"))
            }
        }
    }
}