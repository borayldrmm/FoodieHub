package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.viewmodels.FoodDetailViewModel

@Composable
fun FoodDetailScreen(
    navController: NavController,
    foodId: Int,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val uiState by foodDetailViewModel.uiState.collectAsState()

    LaunchedEffect(foodId) {
        foodDetailViewModel.loadFood(foodId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(R.string.back_txt),
                                    tint = Color.DarkGray
                                )
                            }

                            Column(
                                modifier = Modifier.padding(18.dp)
                            ) {

                                // Food Image
                                Image(
                                    painter = painterResource(food.imageResource),
                                    contentDescription = food.name,
                                    modifier = Modifier
                                        .size(300.dp)
                                        .align(Alignment.CenterHorizontally)
                                )

                                // Food name placeholder
                                Text(
                                    text = food.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                // Rating placeholder
                                Text(
                                    text = "⭐ ${food.rating} - ${food.preparationTimeMinutes} dk",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                // Food description
                                food.detailedDescription?.let {
                                    Text(text = stringResource(it))
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(18.dp),
                        ) {
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                shape = RoundedCornerShape(25),
                                modifier = Modifier.size(width = 125.dp, height = 60.dp)
                            ) {
                                Text(
                                    text = "${food.price} ₺",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(40.dp))

                            Button(
                                onClick = {
                                    navController.navigate(Route.Customization.createRoute(food.id))
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.DarkGray
                                ),
                                shape = RoundedCornerShape(25),
                                modifier = Modifier.size(width = 210.dp, height = 60.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.add_cart_txt),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp

                                )
                            }
                        }
                    }
                }
            }
        }
    }
}