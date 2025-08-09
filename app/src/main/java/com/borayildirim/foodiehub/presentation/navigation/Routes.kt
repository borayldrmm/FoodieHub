package com.borayildirim.foodiehub.presentation.navigation

sealed class Route(val route: String) {
    object Splash: Route("splash")
    object Home: Route("home")
}