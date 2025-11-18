package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.usecase.food.GetFavoriteFoodsUseCase
import com.borayildirim.foodiehub.domain.usecase.food.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the UI state for the Favorites screen.
 *
 * @property favoriteFoods The list of favorite foods.
 * @property isLoading Whether the data is currently being loaded.
 * @property error An error message, if any.
 */
data class FavoritesUiState(
    val favoriteFoods: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for the Favorites screen.
 *
 * This ViewModel manages the state of the Favorites screen, including the list of favorite foods.
 * It uses [GetFavoriteFoodsUseCase] to retrieve the favorite foods and [ToggleFavoriteUseCase]
 * to add or remove a food from favorites.
 *
 * @property getFavoriteFoodsUseCase The use case to get the list of favorite foods.
 * @property toggleFavoriteUseCase The use case to toggle a food's favorite status.
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteFoodsUseCase: GetFavoriteFoodsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    /**
     * The UI state for the Favorites screen.
     */
    val uiState = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    /**
     * Loads the list of favorite foods.
     */
    fun loadFavorites() {
        viewModelScope.launch {
            try {
                getFavoriteFoodsUseCase().collect { favorites ->
                    _uiState.value = _uiState.value.copy(
                        favoriteFoods = favorites,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Toggles the favorite status of a food.
     *
     * @param foodId The ID of the food to toggle.
     */
    fun toggleFavorite(foodId: Int) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(foodId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }
}