package com.borayildirim.foodiehub.domain.usecase.cart

import com.borayildirim.foodiehub.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for updating cart item quantity
 *
 * @param itemId Cart item identifier
 * @param quantity New quantity, must be positive
 */
@Singleton
class UpdateCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(itemId: String, quantity: Int) {
        cartRepository.updateQuantity(itemId, quantity)
    }
}