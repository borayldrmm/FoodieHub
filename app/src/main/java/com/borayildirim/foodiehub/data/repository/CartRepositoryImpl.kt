package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(): CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    private val _totalPrice = MutableStateFlow(0.0)
    private val _cartItemCount = MutableStateFlow(0)

    private fun updateCartMetrics(items: List<CartItem>) {
        _totalPrice.value = items.sumOf { it.totalPrice }
        _cartItemCount.value = items.sumOf { it.quantity }
    }

    override fun addToCart(food: Food, quantity: Int) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.food.id == food.id }

        if (existingItem != null) {
            currentItems.remove(existingItem)
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
            currentItems.add(updatedItem)
            _cartItems.value = currentItems
            updateCartMetrics(currentItems)
        } else {
            val itemId = UUID.randomUUID().toString()
            currentItems.add(
                CartItem(
                    food = food,
                    quantity = quantity,
                    itemId = itemId
                )
            )
            _cartItems.value = currentItems
            updateCartMetrics(currentItems)
        }
    }

    override fun removeFromCart(itemId: String) {
        val currentItems = _cartItems.value.toMutableList()
        val itemToRemove = currentItems.find { it.itemId == itemId }

        if (itemToRemove != null) {
            currentItems.remove(itemToRemove)
            _cartItems.value = currentItems
            updateCartMetrics(currentItems)
        }
    }

    override fun updateQuantity(itemId: String, newQuantity: Int) {
        val currentItems = _cartItems.value.toMutableList()
        val itemIndex = currentItems.indexOfFirst { it.itemId == itemId }

        if (itemIndex != -1) {
            if (newQuantity > 0) {
                currentItems[itemIndex] = currentItems[itemIndex].copy(quantity = newQuantity)
            } else {
                currentItems.removeAt(itemIndex)
            }

            _cartItems.value = currentItems
            updateCartMetrics(currentItems)
        }
    }

    override fun clearCart() {
        _cartItems.value = emptyList()
        updateCartMetrics(emptyList())
    }

    override fun getCartItems(): StateFlow<List<CartItem>> = _cartItems

    override fun getTotalPrice(): StateFlow<Double> {
        return _totalPrice
    }

    override fun getCartItemCount(): StateFlow<Int> {
        return _cartItemCount
    }
}