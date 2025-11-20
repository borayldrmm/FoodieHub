package com.borayildirim.foodiehub.domain.usecase.cart

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for adding food items to cart
 *
 * Creates CartItem with default quantity and customization,
 * suitable for quick order functionality.
 */
@Singleton
class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.addToCart(cartItem)
    }
}