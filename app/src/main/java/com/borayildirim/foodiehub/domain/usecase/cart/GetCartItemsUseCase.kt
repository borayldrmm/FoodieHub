package com.borayildirim.foodiehub.domain.usecase.cart

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for retrieving cart items with reactive updates
 *
 * Returns Flow for automatic UI synchronization when cart changes.
 */
@Singleton
class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return cartRepository.getCartItems()
    }
}