package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.model.MockFoodData
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(): FoodRepository {

    // In-memory favorite storage
    private val favoriteIds = mutableSetOf<Int>()
    override suspend fun getFoods(): List<Food> {
        val allFoods = MockFoodData.getBurgers() +
                MockFoodData.getPizza() +
                MockFoodData.getSalad() +
                MockFoodData.getDrinks()

        // Update favorite status based on favoriteIds
        return allFoods.map { food ->
            food.copy(isFavorite = favoriteIds.contains(food.id))
        }
    }

    override suspend fun getFood(foodId: Int): Food? {
        return getFoods().find { it.id == foodId }
    }

    override suspend fun getCategories(): List<Category> {
        return listOf(
            Category(id = 1, name = "All", isSelected = true),
            Category(id = 2, name = "Burgers", isSelected = false),
            Category(id = 3, name = "Pizzas", isSelected = false),
            Category(id = 4, name = "Salads", isSelected = false),
            Category(id = 5, name = "Drinks", isSelected = false)
        )
    }

    // SET operation - Remove & Add
    override suspend fun toggleFavorite(foodId: Int) {
        if (favoriteIds.contains(foodId)) {
            favoriteIds.remove(foodId)
        } else {
            favoriteIds.add(foodId)
        }
    }

    override suspend fun searchFoods(query: String): List<Food> {
        val foods = getFoods()
        return foods.filter { it.name.contains(query, ignoreCase = true) }
    }

    // READ operation - Get list
    override suspend fun getFavoriteFoods(): List<Food> {
        return getFoods().filter { it.isFavorite }
    }


}