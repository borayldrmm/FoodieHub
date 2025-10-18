package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    operator fun invoke(query: String): Flow<List<Food>> {
        return foodRepository.searchFoods(query)
    }
}