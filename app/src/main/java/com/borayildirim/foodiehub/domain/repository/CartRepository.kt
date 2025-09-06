package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    fun addToCart(food: Food, quantity: Int = 1)
    fun removeFromCart(itemId: String)
    fun updateQuantity(itemId: String, newQuantity: Int)
    fun clearCart()
    fun getCartItems(): StateFlow<List<CartItem>>
    fun getTotalPrice(): StateFlow<Double>
    fun getCartItemCount(): StateFlow<Int>

}