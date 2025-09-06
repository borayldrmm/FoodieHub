package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.model.SideOption
import com.borayildirim.foodiehub.domain.model.Topping
import com.borayildirim.foodiehub.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class CustomizationUiState(
    val food: Food? = null,
    val selectedToppings: List<Topping> = emptyList(),
    val selectedSideOptions: List<SideOption> = emptyList(),
    val spicyLevel: Float = 0.5f,
    val portion: Int = 1,
    val totalPrice: Double = 0.0
)

@HiltViewModel
class CustomizationViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CustomizationUiState())
    val uiState = _uiState.asStateFlow()


    private fun calculateTotalPrice(
        food: Food,
        toppings: List<Topping>,
        sideOption: List<SideOption>,
        portion: Int
    ): Double {
        val basePrice = food.price
        val sidePrice = sideOption.sumOf { it.price }
        return (basePrice + sidePrice) * portion
    }

    fun initializeFood(food: Food) {
        val defaultToppings = food.availableToppings.filter { it.isIncluded }
        _uiState.value = _uiState.value.copy(
            food = food,
            selectedToppings = defaultToppings,
            totalPrice = calculateTotalPrice(food, defaultToppings, emptyList(), 1)
        )
    }

    fun toggleTopping(topping: Topping) {
        val currentState = _uiState.value
        val updatedToppings = if (currentState.selectedToppings.contains(topping)) {
            currentState.selectedToppings - topping
        } else {
            currentState.selectedToppings + topping
        }

        val newTotalPrice = calculateTotalPrice(
            currentState.food!!,
            updatedToppings,
            currentState.selectedSideOptions,
            currentState.portion
        )

        _uiState.value = currentState.copy(
            selectedToppings = updatedToppings,
            totalPrice = newTotalPrice
        )
    }

    fun toggleSideoption(sideOption: SideOption) {
        val currentState = _uiState.value
        val updatedSides = if (currentState.selectedSideOptions.contains(sideOption)) {
            currentState.selectedSideOptions - sideOption
        } else {
            currentState.selectedSideOptions + sideOption
        }

        val newTotalPrice = calculateTotalPrice(
            currentState.food!!,
            currentState.selectedToppings,
            updatedSides,
            currentState.portion
        )

        _uiState.value = currentState.copy(
            selectedSideOptions = updatedSides,
            totalPrice = newTotalPrice
        )
    }

    fun updateSpicyLevel(level: Float) {
        _uiState.value = _uiState.value.copy(spicyLevel = level)
    }

    fun updatePortion(newPortion: Int) {
        val currentState = _uiState.value
        if (newPortion > 0) {
            val newTotalPrice = calculateTotalPrice(
                currentState.food!!,
                currentState.selectedToppings,
                currentState.selectedSideOptions,
                newPortion
            )

            _uiState.value = currentState.copy(
                portion = newPortion,
                totalPrice = newTotalPrice
            )
        }
    }

    fun addToCart() {
        val state = _uiState.value
        state.food?.let {food ->
            // Add Customized Food to Card logic
            cartRepository.addToCart(food, state.portion)
        }
    }
}