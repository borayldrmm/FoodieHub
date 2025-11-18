package com.borayildirim.foodiehub.domain.usecase.cart

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.addToCart(cartItem)
    }
}