package com.borayildirim.foodiehub.presentation.navigation

sealed class Route(val route: String) {
    data object Splash: Route("splash")

    data object Login: Route("login")

    data object Register: Route("register")

    data object Home: Route("home?openQuickOrder={openQuickOrder}") {
        fun createRoute(openQuickOrder: Boolean = false) =
            "home?openQuickOrder=$openQuickOrder"
    }

    data object Cart: Route("cart")

    data object Profile: Route("profile")

    data object Favorites: Route("favorites")

    data object Add: Route("add")

    data object Payment: Route("payment")

    data object PaymentDetails: Route("payment_details")

    data object FoodDetail: Route("food_detail/{foodId}") {
        fun createRoute(foodId: Int) = "food_detail/$foodId"
    }

    data object Customization: Route("customization_screen/{foodId}") {
        fun createRoute(foodId: Int) = "customization_screen/$foodId"
    }

    data object OrderHistory : Route("order_history")

    data object OrderDetail : Route("order_detail/{orderId}") {
        fun createRoute(orderId: String) = "order_detail/$orderId"
    }

    data object AddressListRoute : Route("address_list")
    data object AddAddressRoute : Route("add_address")
}