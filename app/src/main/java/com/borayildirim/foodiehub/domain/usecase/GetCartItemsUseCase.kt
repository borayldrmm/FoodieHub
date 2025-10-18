package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCartItemsUseCase @Inject constructor(
  private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return cartRepository.getCartItems()
    }
}