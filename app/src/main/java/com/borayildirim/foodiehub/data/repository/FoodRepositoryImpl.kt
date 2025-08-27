package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.model.MockFoodData
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(): FoodRepository {
    override suspend fun getFoods(): List<Food> {
        return MockFoodData.getBurgers() +
                MockFoodData.getPizza() +
                MockFoodData.getSalad() +
                MockFoodData.getDrinks()
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

    override suspend fun toggleFavorite(foodId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun searchFoods(query: String): List<Food> {
        val foods = getFoods()
        return foods.filter { it.name.contains(query, ignoreCase = true) }
    }


}