package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for shopping cart operations
 *
 * Provides reactive cart management with Flow-based updates.
 * Implementations handle food data merging and persistence.
 */
interface CartRepository {
    /**
     * Retrieves cart items with full food details as reactive Flow
     */
    fun getCartItems(): Flow<List<CartItem>>

    suspend fun addToCart(cartItem: CartItem)
    suspend fun removeFromCart(itemId: String)
    suspend fun updateQuantity(itemId: String, quantity: Int)

    /**
     * Clears entire cart, typically called after order completion
     */
    suspend fun clearCart()
}