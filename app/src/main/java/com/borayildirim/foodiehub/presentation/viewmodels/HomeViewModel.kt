package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


const val CATEGORY_ALL_ID = 1
data class HomeUiState (
    val foods: List<Food> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val selectedCategoryId: Int = 1, // "All" category
    val isFilterExpanded: Boolean = false,
    val allFoods: List<Food> = emptyList()
)


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val TURKISH_LOCALE: Locale = Locale.forLanguageTag("tr-TR")

    fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val foodsFromRepo = foodRepository.getFoods()
                val categoriesFromRepo = foodRepository.getCategories()

                val current = _uiState.value
                val derivedFoods = filterFoods(
                    allFoods = foodsFromRepo,
                    selectedCategoryId = current.selectedCategoryId,
                    searchQuery = current.searchQuery
                )

                _uiState.value = current.copy(
                    allFoods = foodsFromRepo,
                    foods = derivedFoods,
                    categories = categoriesFromRepo,
                    isLoading = false
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }


    fun searchFoods(query: String) {
        val current = _uiState.value
        val newFoods = filterFoods(
            allFoods = current.allFoods,
            selectedCategoryId = current.selectedCategoryId,
            searchQuery = query
        )
        _uiState.value = current.copy(
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

        val updatedCategories = _uiState.value.categories.map {category ->
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
                // Update the favorite state in the Repository
                foodRepository.toggleFavorite(foodId)

                // Get fresh data (with updated favorite situation in the Repository)
                loadInitialData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}