package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(itemId: String) {
        cartRepository.removeFromCart(itemId)
    }
}