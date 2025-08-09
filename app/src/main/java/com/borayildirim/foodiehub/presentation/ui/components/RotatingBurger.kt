package com.borayildirim.foodiehub.presentation.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import com.borayildirim.foodiehub.R

@Composable
fun RotatingBurger(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "")

    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Image(
        painter = painterResource(id = R.drawable.rotating_burger),
        contentDescription = stringResource(R.string.splash_loading),
        modifier = modifier
            .graphicsLayer(
                rotationZ = angle,
                transformOrigin = TransformOrigin.Center
            )
            .semantics {
                progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
            }
    )
}