package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.usecase.cart.AddToCartUseCase
import com.borayildirim.foodiehub.domain.usecase.food.GetFoodByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class FoodDetailUiState(
    val food: Food? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val spicyLevel: Float = 0.5f,
    val portion: Int = 1,
    val totalPrice: Double = 0.0
)

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val getFoodByIdUseCase: GetFoodByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(FoodDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadFood(foodId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                getFoodByIdUseCase(foodId).collect { food ->
                    if (food != null) {
                        val initialPrice = calculateTotalPrice(food, 1)
                        _uiState.value = _uiState.value.copy(
                            food = food,
                            isLoading = false,
                            totalPrice = initialPrice
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "Cannot find food"
                        )
                    }
                }
            }catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error!"
                )
            }
        }
    }

    private fun calculateTotalPrice(food: Food, portion: Int): Double {
        return food.price * portion
    }

    fun updateSpicyLevel(spicyLevel: Float) {
        _uiState.value = _uiState.value.copy(spicyLevel = spicyLevel)
    }

    fun updatePortion(newPortion: Int) {
        val currentState = _uiState.value
        if (newPortion > 0 && currentState.food != null) {
            val newTotalPrice = calculateTotalPrice(currentState.food, newPortion)
            _uiState.value = currentState.copy(
                portion = newPortion,
                totalPrice = newTotalPrice
            )
        }
    }

    fun addToCart() {
        val state = _uiState.value
        state.food?.let { food ->
            viewModelScope.launch {
                try {
                    val cartItem = CartItem(
                        food = food,
                        quantity = state.portion,
                        itemId = UUID.randomUUID().toString()
                    )
                    addToCartUseCase(cartItem)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}