package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FoodDetailScreen(
    navController: NavController,
    foodId: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Food Detail Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Food ID: $foodId",
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text("Back")
        }
    }
}