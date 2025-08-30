package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    suspend fun addToCart(food: Food, quantity: Int = 1)
    suspend fun removeFromCart(itemId: String)
    suspend fun updateQuantity(itemId: String, newQuantity: Int)
    suspend fun clearCart()
    fun getCartItems(): StateFlow<List<CartItem>>
    fun getTotalPrice(): StateFlow<Double>
    fun getCartItemCount(): StateFlow<Int>

}