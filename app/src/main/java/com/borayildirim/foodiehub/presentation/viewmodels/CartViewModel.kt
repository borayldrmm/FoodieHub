package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CartUiState(
    val cartItems: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val itemCount: Int = 0,
    val isLoading: Boolean = false
)


@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
           combine(
               cartRepository.getCartItems(),
               cartRepository.getTotalPrice(),
               cartRepository.getCartItemCount()
           ) { items, price, count ->
               CartUiState(
                   cartItems = items,
                   totalPrice = price,
                   itemCount = count,
                   isLoading = false
               )
           }.collect { newState ->
               _uiState.value = newState
           }
        }
    }


    fun addToCart(food: Food, quantity: Int = 1) {
        cartRepository.addToCart(food,quantity)
    }

    fun removeFromCart(itemId: String) {
        cartRepository.removeFromCart(itemId)
    }

    fun updateQuantity(itemId: String, newQuantity: Int) {
        cartRepository.updateQuantity(itemId, newQuantity)
    }

    fun clearCart() {
        cartRepository.clearCart()
    }
}