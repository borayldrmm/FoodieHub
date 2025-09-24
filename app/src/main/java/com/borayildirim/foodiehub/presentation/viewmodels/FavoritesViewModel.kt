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

data class FavoritesUiState(
    val favoriteFoods: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState = _uiState.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val favorites = foodRepository.getFavoriteFoods()
                _uiState.value = _uiState.value.copy(
                    favoriteFoods = favorites,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun toggleFavorite(foodId: Int) {
        viewModelScope.launch {
            try {
                foodRepository.toggleFavorite(foodId)
                loadFavorites() // Refresh favorites list
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}