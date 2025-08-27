package com.borayildirim.foodiehub.presentation.navigation

sealed class Route(val route: String) {
    data object Splash: Route("splash")
    data object Home: Route("home")
    data object Cart: Route("cart")
    data object Profile: Route("profile")
    data object Favorites: Route("favorites")
    data object Add: Route("add")
    data object FoodDetail: Route("food_detail/{foodId}") {
        fun createRoute(foodId: Int) = "food_detail/$foodId"
    }
}