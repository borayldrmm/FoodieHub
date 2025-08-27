package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food

interface FoodRepository {
    suspend fun getFoods(): List<Food>
    suspend fun getCategories(): List<Category>
    suspend fun toggleFavorite(foodId: Int)
    suspend fun searchFoods(query: String): List<Food>
}