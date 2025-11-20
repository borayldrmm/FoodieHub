package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.usecase.food.GetAllFoodsUseCase
import com.borayildirim.foodiehub.domain.usecase.food.ToggleFavoriteUseCase
import com.borayildirim.foodiehub.domain.usecase.user.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


/**
 * ViewModel for Home screen managing food catalog and user profile
 *
 * Features:
 * - Food search and category filtering
 * - Real-time profile image sync from ProfileScreen
 * - Favorite toggle functionality
 */

const val CATEGORY_ALL_ID = 1
data class HomeUiState (
    val foods: List<Food> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val selectedCategoryId: Int = 1, // "All" category
    val isFilterExpanded: Boolean = false,
    val allFoods: List<Food> = emptyList(),
    val userProfileImage: String? = null,
    val showQuickOrderSheet: Boolean = false
)


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val TURKISH_LOCALE: Locale = Locale.forLanguageTag("tr-TR")

    init {
        loadInitialData()
        loadUserProfile()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                getAllFoodsUseCase().collect { foodsFromRepo ->
                    val current = _uiState.value
                    val derivedFoods = filterFoods(
                        allFoods = foodsFromRepo,
                        selectedCategoryId = current.selectedCategoryId,
                        searchQuery = current.searchQuery
                    )

                    _uiState.value = current.copy(
                        allFoods = foodsFromRepo,
                        foods = derivedFoods,
                        categories = getDefaultCategories(),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun getDefaultCategories(): List<Category> {
        return listOf(
            Category(id = 1, name = "All", isSelected = true),
            Category(id = 2, name = "Burgers", isSelected = false),
            Category(id = 3, name = "Pizzas", isSelected = false),
            Category(id = 4, name = "Salads", isSelected = false),
            Category(id = 5, name = "Drinks", isSelected = false)
        )
    }

    fun searchFoods(query: String) {
        val current = _uiState.value
        val newFoods = filterFoods(
            allFoods = current.allFoods,
            selectedCategoryId = current.selectedCategoryId,
            searchQuery = query
        )
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            foods = newFoods
        )
    }

    private fun filterFoods(
        allFoods: List<Food>,
        selectedCategoryId: Int,
        searchQuery: String
    ): List<Food> {
        val byCategory = if (selectedCategoryId == CATEGORY_ALL_ID) {
            allFoods
        } else {
            allFoods.filter { it.categoryId == selectedCategoryId }
        }

        if (searchQuery.isBlank()) return byCategory

        val query = searchQuery.trim().lowercase(TURKISH_LOCALE)
        return byCategory.filter { it.name.lowercase(TURKISH_LOCALE).contains(query) }
    }


    fun selectCategory(categoryId: Int) {
        val current = _uiState.value

        val updatedCategories = current.categories.map {category ->
            category.copy(isSelected = category.id == categoryId)
        }

        val recomputedFoods = filterFoods(
            allFoods = current.allFoods,
            selectedCategoryId = categoryId,
            searchQuery = current.searchQuery
        )

        _uiState.value = current.copy(
            selectedCategoryId = categoryId,
            categories = updatedCategories,
            foods = recomputedFoods
        )
    }


    fun toggleFilterExpansion() {
        _uiState.value = _uiState.value.copy(
            isFilterExpanded = !_uiState.value.isFilterExpanded
        )
    }


    fun toggleFavorite(foodId: Int) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(foodId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val userId = userPreferencesManager.getUserId().firstOrNull()
                if (userId != null) {
                    getUserByIdUseCase(userId).collect { user ->
                        _uiState.update {
                            it.copy(
                                userProfileImage = user?.profilePicture
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Silent fail for profile image
                e.printStackTrace()
            }
        }
    }

    fun showQuickOrderSheet() {
        _uiState.value = _uiState.value.copy(showQuickOrderSheet = true)
    }

    fun hideQuickOrderSheet() {
        _uiState.value = _uiState.value.copy(showQuickOrderSheet = false)
    }

    fun getFavoriteFoods(): List<Food> {
        return _uiState.value.allFoods.filter { it.isFavorite }
    }
}