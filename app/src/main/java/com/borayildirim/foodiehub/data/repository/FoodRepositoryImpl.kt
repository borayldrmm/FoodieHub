package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.FoodDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.model.MockFoodData
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao
): FoodRepository {

    // In-memory favorite storage
    private val favoriteIds = mutableSetOf<Int>()
    override fun getFoods(): Flow<List<Food>> {
        return foodDao.getAllFoods().map { entities ->
            entities.map { entity->
                val mockFood = MockFoodData.getAllFoods().find { it.id == entity.id }
                entity.toDomain(
                    description = mockFood?.description,
                    detailedDescription = mockFood?.detailedDescription,
                    isFavorite = favoriteIds.contains(entity.id),
                    availableToppings = mockFood?.availableToppings ?: emptyList(),
                    availableSideOptions = mockFood?.availableSideOptions ?: emptyList()
                )
            }
        }
    }

    override fun getFood(foodId: Int): Flow<Food?> {
        return foodDao.getFoodById(foodId).map { entity ->
            entity?.let {
                val mockFood = MockFoodData.getAllFoods().find { it.id == foodId }
                it.toDomain(
                    description = mockFood?.description,
                    detailedDescription = mockFood?.detailedDescription,
                    isFavorite = favoriteIds.contains(foodId),
                    availableToppings = mockFood?.availableToppings ?: emptyList(),
                    availableSideOptions = mockFood?.availableSideOptions ?: emptyList()
                )
            }
        }
    }

    override fun getCategories(): Flow<List<Category>> = flow {
        emit(
            listOf(
                Category(id = 1, name = "All", isSelected = true),
                Category(id = 2, name =  "Burgers", isSelected = false),
                Category(id = 3, name = "Pizzas", isSelected = false),
                Category(id = 4, name = "Salads", isSelected = false),
                Category(id = 5, name = "Drinks", isSelected = false)
            )
        )
    }

    override fun getFavoriteFoods(): Flow<List<Food>> {
        return getFoods().map { foods ->
            foods.filter { it.isFavorite }
        }
    }

    override fun searchFoods(query: String): Flow<List<Food>> {
        return foodDao.searchFoods(query).map { entities ->
            entities.map { entity ->
                val mockFood = MockFoodData.getAllFoods().find { it.id == entity.id }
                entity.toDomain(
                    description = mockFood?.description,
                    detailedDescription = mockFood?.detailedDescription,
                    isFavorite = favoriteIds.contains(entity.id),
                    availableToppings = mockFood?.availableToppings ?: emptyList(),
                    availableSideOptions = mockFood?.availableSideOptions ?: emptyList()
                )
            }
        }
    }

    override suspend fun toggleFavorite(foodId: Int) {
        if (favoriteIds.contains(foodId)) {
            favoriteIds.remove(foodId)
        } else {
            favoriteIds.add(foodId)
        }
    }

}