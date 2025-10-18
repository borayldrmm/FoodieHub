package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.repository.FoodRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToggleFavoriteUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(foodId: Int) {
        foodRepository.toggleFavorite(foodId)
    }
}