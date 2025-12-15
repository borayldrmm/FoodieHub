package com.borayildirim.foodiehub.domain.usecase.food

import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for retrieving food by ID with reactive updates
 */
@Singleton
class GetFoodByIdUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    operator fun invoke(foodId: Int): Flow<Food?> {
        return foodRepository.getFood(foodId)
    }
}