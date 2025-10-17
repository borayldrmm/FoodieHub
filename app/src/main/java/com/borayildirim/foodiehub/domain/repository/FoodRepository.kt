package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoods(): Flow<List<Food>>

    fun getFood(foodId: Int): Flow<Food?>

    fun getCategories(): Flow<List<Category>>

    fun getFavoriteFoods(): Flow<List<Food>>
    fun searchFoods(query: String): Flow<List<Food>>

    suspend fun toggleFavorite(foodId: Int)
}