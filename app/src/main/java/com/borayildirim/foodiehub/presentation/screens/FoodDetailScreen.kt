package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.viewmodels.CartViewModel
import com.borayildirim.foodiehub.presentation.viewmodels.FoodDetailViewModel

@Composable
fun FoodDetailScreen(
    navController: NavController,
    foodId: Int,
    cartViewModel: CartViewModel = hiltViewModel(),
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val uiState by foodDetailViewModel.uiState.collectAsState()

    LaunchedEffect(foodId) {
        foodDetailViewModel.loadFood(foodId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            uiState.isLoading -> {
                Text(stringResource(R.string.loading_txt))
            }

            uiState.error != null -> {
                Text("Hata: ${uiState.error}")
            }

            uiState.food != null -> {
                uiState.food?.let {food ->
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Fiyat: ${food.price} ₺",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    food.description?.let { desc ->
                        Text(
                            text = desc.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            navController.navigate(Route.Customization.createRoute(food.id))
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(stringResource(R.string.customize_txt))
                    }
                }

            }
        }

        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text(stringResource(R.string.back_txt))
        }
    }
}