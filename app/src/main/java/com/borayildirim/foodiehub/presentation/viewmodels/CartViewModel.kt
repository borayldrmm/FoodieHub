package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.usecase.AddToCartUseCase
import com.borayildirim.foodiehub.domain.usecase.ClearCartUseCase
import com.borayildirim.foodiehub.domain.usecase.GetCartItemsUseCase
import com.borayildirim.foodiehub.domain.usecase.RemoveFromCartUseCase
import com.borayildirim.foodiehub.domain.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            getCartItemsUseCase().collect { items ->
                val totalPrice = items.sumOf { it.totalPrice }
                val itemCount = items.sumOf { it.quantity }

                _uiState.value = CartUiState(
                    cartItems = items,
                    totalPrice = totalPrice,
                    itemCount = itemCount,
                    isLoading = false
                )
            }
        }
    }


    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            try {
                addToCartUseCase(cartItem)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFromCart(itemId: String) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase(itemId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            try {
                updateCartQuantityUseCase(itemId, newQuantity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            try {
                clearCartUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}