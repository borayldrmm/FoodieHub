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
            style = MaterialTheme.typography.splashTitle
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodieHubTheme {
        SplashContent()
    }
}