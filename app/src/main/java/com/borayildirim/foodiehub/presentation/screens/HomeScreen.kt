package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeCategoriesFilter
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeFoodsGrid
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeHeader
import com.borayildirim.foodiehub.presentation.ui.components.home.HomeSearchBar
import com.borayildirim.foodiehub.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel =hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadInitialData()
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
            HomeHeader(onProfileClick = {
                navController.navigate(Route.Profile.route)
            })
            HomeSearchBar(
                query = uiState.searchQuery,
                onQueryChange = { newQuery ->
                    viewModel.searchFoods(newQuery)
                },
                onFilterClick = {
                    viewModel.toggleFilterExpansion() // <-- Empty {} --> real function
                },
                isFilterActive = uiState.isFilterExpanded // <-- false --> dynamic value
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