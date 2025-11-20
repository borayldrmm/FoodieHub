package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeCategoriesFilter
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeFoodsGrid
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeHeader
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeSearchBar
import com.borayildirim.foodiehub.presentation.ui.components.home.QuickOrderBottomSheet
import com.borayildirim.foodiehub.presentation.viewmodels.HomeViewModel

/**
 * Home screen displaying food catalog with search and filtering capabilities
 *
 * Main landing screen after authentication showing:
 * - User profile image in header (synced with ProfileScreen)
 * - Search bar for food items
 * - Category filter with expandable options
 * - Grid of food items with favorites functionality
 *
 * Features:
 * - Real-time search across food items
 * - Category-based filtering (All, Burgers, Pizzas, Salads, Drinks)
 * - Toggle favorite status for food items
 * - Navigation to food details and profile screens
 * - Dynamic profile image updates from ProfileScreen
 *
 * @param navController Navigation controller for screen transitions
 *
 * @see HomeViewModel for state management
 * @see HomeHeader for branding and profile image display
 * @see HomeSearchBar for search functionality
 * @see HomeCategoriesFilter for category selection
 * @see HomeFoodsGrid for food items display
 */
@Composable
fun HomeScreen(
    navController: NavController,
    openQuickOrderOnStart: Boolean = false
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    // Auto-open Quick Order Sheet if arguments is true
    LaunchedEffect(openQuickOrderOnStart) {
        if (openQuickOrderOnStart) {
            viewModel.showQuickOrderSheet()
        }
    }

    // Quick Order Bottom Sheet
    if (uiState.showQuickOrderSheet) {
        QuickOrderBottomSheet(
            favoriteFoods = viewModel.getFavoriteFoods(),
            cartItemCount = uiState.cartItemCount,
            onDismiss = { viewModel.hideQuickOrderSheet() },
            onAddToCart = { food ->
                // TODO: Add to cart logic (add later)
                viewModel.addToCart(food)
            },
            onGoToCart = {
                viewModel.hideQuickOrderSheet()
                navController.navigate(Route.Cart.route)
            }
        )
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            HomeHeader(
                profileImageUrl = uiState.userProfileImage,
                onProfileClick = {
                    navController.navigate(Route.Profile.route)
                }
            )

            HomeSearchBar(
                query = uiState.searchQuery,
                onQueryChange = { newQuery ->
                    viewModel.searchFoods(newQuery)
                },
                onFilterClick = {
                    viewModel.toggleFilterExpansion()
                },
                isFilterActive = uiState.isFilterExpanded
            )

            if (uiState.isFilterExpanded) {
                HomeCategoriesFilter(
                    categories = uiState.categories,
                    onCategoryClick = { categoryId ->
                        viewModel.selectCategory(categoryId)
                    }
                )
            }

            HomeFoodsGrid(
                foods = uiState.foods,
                onFoodClick = { food ->
                    navController.navigate(Route.FoodDetail.createRoute(food.id))
                },
                onFavoriteClick = { foodId ->
                    viewModel.toggleFavorite(foodId)
                }
            )
        }
    }
}