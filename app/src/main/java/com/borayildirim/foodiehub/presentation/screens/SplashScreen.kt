package com.borayildirim.foodiehub.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.theme.SplashBgGradientFirst
import com.borayildirim.foodiehub.presentation.theme.SplashBgGradientSecond
import com.borayildirim.foodiehub.presentation.theme.appNameTitle
import com.borayildirim.foodiehub.presentation.ui.components.RotatingBurger
import com.borayildirim.foodiehub.presentation.viewmodels.SplashViewModel

/**
 * Configuration for decorative burger images with responsive ratios
 * Base calculations assume dynamic screen width adaptation
 */
object BurgerImageConfig {
    data class ImageSpec(
        val resId: Int,
        val widthRatio: Float,
        val aspectRatio: Float,
        val paddingRatio: Float
    )

    val images = listOf(
        ImageSpec(
            resId = R.drawable.splash_screen_burger_1,
            widthRatio = 0.5f,
            aspectRatio = 1.235f,   // Original: 200x245 -> height/width ratio
            paddingRatio = 0f
        ),
        ImageSpec(
            resId = R.drawable.splash_screen_burger_shadow_1,
            widthRatio = 0.2f,
            aspectRatio = 2.4f,
            paddingRatio = 0f
        ),
        ImageSpec(
            resId = R.drawable.splash_screen_burger_shadow_2,
            widthRatio = 0.20f,
            aspectRatio = 1.6f,
            paddingRatio = 0.25f
        ),
        ImageSpec(
            resId = R.drawable.splash_screen_burger_shadow_3,
            widthRatio = 0.3f,
            aspectRatio = 0.15f,
            paddingRatio = 0.47f
        ),
        ImageSpec(
            resId = R.drawable.splash_screen_burger_2,
            widthRatio = 1f,
            aspectRatio = 0.38f,
            paddingRatio = 0f
        )
    )
}

data class BurgerImageData(
    val resId: Int,
    val startPadding: Int,
    val width: Int,
    val height: Int
)

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

    SplashContent()

}

@Composable
fun SplashContent() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    SplashBgGradientFirst,
                    SplashBgGradientSecond
                )
            )
        )
    ) {
        CenterContent(modifier = Modifier.align(Alignment.Center))
        DecorativeBurgers()
    }
}

@Composable
fun CenterContent(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.appNameTitle
        )
        Spacer(Modifier.size(20.dp))
        RotatingBurger(modifier = Modifier.size(70.dp))
    }
}

@Composable
fun BoxScope.DecorativeBurgers() {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val burgerImages = remember(screenWidth) {
        BurgerImageConfig.images.map { spec ->
            val width = (screenWidth * spec.widthRatio).toInt()
            val height = (width * spec.aspectRatio).toInt()
            val padding = (screenWidth * spec.paddingRatio).toInt()

            BurgerImageData(
                resId = spec.resId,
                startPadding = padding,
                width = width,
                height = height
            )
        }
    }

    burgerImages.forEach { burgerData ->
        Image(
            painter = painterResource(id = burgerData.resId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = burgerData.startPadding.dp)
                .size(width = burgerData.width.dp, height = burgerData.height.dp)
        )
    }
}