package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.borayildirim.foodiehub.presentation.viewmodels.SplashViewModel

@Composable
fun SplashScreen() {
    val viewModel = hiltViewModel<SplashViewModel>()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()

    ) {
        Text(
            text = "This is a test screen",
            fontSize = 22.sp
        )
    }

}