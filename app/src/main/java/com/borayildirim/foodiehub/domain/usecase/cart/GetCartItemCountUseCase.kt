package com.borayildirim.foodiehub.domain.usecase.cart

import com.borayildirim.foodiehub.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for retrieving total cart item count
 *
 * Returns reactive Flow that emits count whenever cart changes.
 * Used for badge display on navigation icons.
 */
class GetCartItemCountUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Int> {
        return cartRepository.getCartItems().map { items ->
            items.sumOf { it.quantity }
        }
    }
}