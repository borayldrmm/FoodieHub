package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.theme.SplashBgGradientFirst
import com.borayildirim.foodiehub.presentation.theme.SplashBgGradientSecond
import com.borayildirim.foodiehub.presentation.theme.splashTitle
import com.borayildirim.foodiehub.presentation.viewmodels.SplashViewModel

@Composable
fun SplashScreen(navController: NavController) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val shouldNavigate by viewModel.shouldNavigate.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.startSplash()
    }

    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate) {
            navController.navigate(Route.Home.route) {
                popUpTo(Route.Splash.route) { inclusive = true}
            }
        }
    }

    @Composable
    fun RotatingBurger() {
        val transition = rememberInfiniteTransition(label = "")

        val angle by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200),
                repeatMode = RepeatMode.Restart
            ),
            label = ""
        )

        Image(
            painter = painterResource(id = R.drawable.rotating_burger),
            contentDescription = stringResource(R.string.splash_loading),
            modifier = Modifier
                .graphicsLayer(
                    rotationZ = angle,
                    transformOrigin = TransformOrigin.Center
                )
                .semantics {
                    progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
                }
        )
    }

    Box (modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    SplashBgGradientFirst,
                    SplashBgGradientSecond
                )
            )
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(Modifier.size(250.dp))

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.splashTitle,
                fontSize = 60.sp
            )

            Spacer(Modifier.size(50.dp))

            RotatingBurger()

            Spacer(Modifier.size(150.dp))

            Image(
                painterResource(R.drawable.splash_screen_burger_left),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 246.dp, height = 288.dp)
            )
        }
    }
}