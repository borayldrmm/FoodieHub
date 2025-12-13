package com.borayildirim.foodiehub.domain.usecase.food

import com.borayildirim.foodiehub.domain.repository.FoodRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for toggling food favorite status with database persistence
 */
@Singleton
class ToggleFavoriteUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(foodId: Int) {
        foodRepository.toggleFavorite(foodId)
    }
}