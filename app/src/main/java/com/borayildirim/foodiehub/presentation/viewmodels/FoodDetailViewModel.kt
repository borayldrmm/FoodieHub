package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FoodDetailUiState(
    val food: Food? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FoodDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun loadFood(foodId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val food = foodRepository.getFood(foodId)
                if (food != null) {
                    _uiState.value = _uiState.value.copy(
                        food = food,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Cannot find food"
                    )
                }
            }catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error!"
                )
            }
        }
    }
}