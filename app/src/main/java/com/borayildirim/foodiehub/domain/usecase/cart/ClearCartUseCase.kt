package com.borayildirim.foodiehub.domain.usecase.cart

import com.borayildirim.foodiehub.domain.repository.CartRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke() {
        cartRepository.clearCart()
    }
}