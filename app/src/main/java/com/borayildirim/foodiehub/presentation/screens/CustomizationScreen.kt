package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHostController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.ui.components.customization.PortionControl
import com.borayildirim.foodiehub.presentation.ui.components.customization.SideOptionSection
import com.borayildirim.foodiehub.presentation.ui.components.customization.SpicyLevelControl
import com.borayildirim.foodiehub.presentation.ui.components.customization.ToppingSection
import com.borayildirim.foodiehub.presentation.viewmodels.CustomizationViewModel
import com.borayildirim.foodiehub.presentation.viewmodels.FoodDetailViewModel

@Composable
fun CustomizationScreen(
    navController: NavHostController,
    foodId: Int,
    customizationViewModel: CustomizationViewModel = hiltViewModel(),
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel(),
) {
    val customizationState by customizationViewModel.uiState.collectAsState()
    val foodDetailState by foodDetailViewModel.uiState.collectAsState()

    LaunchedEffect(foodId) {
        foodDetailViewModel.loadFood(foodId)
    }

    LaunchedEffect(foodDetailState.food) {
        foodDetailState.food?.let { food ->
            customizationViewModel.initializeFood(food)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        customizationState.food?.let { food ->
            // Header with back button
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = stringResource(R.string.customization_sc_back_icon))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {

                // Left Side --> Food Image
                Image(
                    painter = painterResource(R.drawable.custom_burger_image),
                    contentDescription = food.name,
                    modifier = Modifier
                        .width(200.dp)
                        .height(270.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Right Side --> Controls

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    //Customize Text
                    Text(
                        text = stringResource(R.string.customization_sc_customize_txt),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    //Spicy Level Slider
                    SpicyLevelControl(
                        spicyLevel = customizationState.spicyLevel,
                        onSpicyLevelChange = { customizationViewModel.updateSpicyLevel(it) }
                    )

                    //Portion Counter
                    PortionControl(
                        portion = customizationState.portion,
                        onPortionChange = { customizationViewModel.updatePortion(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Topping Section
            ToppingSection(
                title = stringResource(R.string.toppings_txt),
                topping = food.availableToppings,
                selectedToppings = customizationState.selectedToppings,
                onToppingToggle = { customizationViewModel.toggleTopping(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Side Options Section
            SideOptionSection(
                title = stringResource(R.string.side_options_txt),
                sideOptions = food.availableSideOptions,
                selectedSideOptions = customizationState.selectedSideOptions,
                onSideOptionToggle = { customizationViewModel.toggleSideoption(it) }
            )

            Spacer(modifier = Modifier.height(125.dp))

            // Bottom section with total and order button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column{
                    Text(
                        text = stringResource(R.string.total_txt),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${customizationState.totalPrice} ₺",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {
                        // CardViewModel Inject
                        customizationViewModel.addToCart()
                        navController.navigate(Route.Home.route) {
                            popUpTo(Route.Home.route) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(70.dp)
                        .width(195.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_cart_txt),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}