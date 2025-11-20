package com.borayildirim.foodiehub.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.borayildirim.foodiehub.presentation.screens.AddAddressScreen
import com.borayildirim.foodiehub.presentation.screens.AddressListScreen
import com.borayildirim.foodiehub.presentation.screens.CartScreen
import com.borayildirim.foodiehub.presentation.screens.CustomizationScreen
import com.borayildirim.foodiehub.presentation.screens.FavoritesScreen
import com.borayildirim.foodiehub.presentation.screens.FoodDetailScreen
import com.borayildirim.foodiehub.presentation.screens.HomeScreen
import com.borayildirim.foodiehub.presentation.screens.LoginScreen
import com.borayildirim.foodiehub.presentation.screens.OrderDetailScreen
import com.borayildirim.foodiehub.presentation.screens.OrderHistoryScreen
import com.borayildirim.foodiehub.presentation.screens.PaymentDetailsScreen
import com.borayildirim.foodiehub.presentation.screens.PaymentScreen
import com.borayildirim.foodiehub.presentation.screens.ProfileScreen
import com.borayildirim.foodiehub.presentation.screens.RegisterScreen
import com.borayildirim.foodiehub.presentation.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Splash.route) { SplashScreen(navController) }

        composable(Route.Login.route) { LoginScreen(navController) }

        composable(Route.Register.route) { RegisterScreen(navController) }

        composable(Route.Add.route) { Text("Add Screen") }

        composable(
            route = Route.Home.route,
            arguments = listOf(
                navArgument("openQuickOrder") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val openQuickOrder = backStackEntry.arguments?.getBoolean("openQuickOrder")
                ?: false

            HomeScreen(
                navController = navController,
                openQuickOrderOnStart = openQuickOrder
            )
        }

        composable(Route.Favorites.route) {
            FavoritesScreen(navController)
        }

        composable(Route.Cart.route) {
            CartScreen(navController)
        }

        composable(
            route = Route.FoodDetail.route,
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
            FoodDetailScreen(navController, foodId)
        }

        composable(
            route = Route.Customization.route,
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
            CustomizationScreen(navController = navController, foodId = foodId)
        }

        composable(Route.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Route.Payment.route) {
            PaymentScreen(navController = navController)
        }

        composable(Route.PaymentDetails.route) {
            PaymentDetailsScreen(navController = navController)
        }

        composable(Route.OrderHistory.route) {
            OrderHistoryScreen(navController = navController)
        }

        composable(
            route = Route.OrderDetail.route,
            arguments = listOf(
                navArgument("orderId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            OrderDetailScreen(
                navController = navController,
                orderId = orderId
            )
        }

        // Address Management Routes
        composable(Route.AddressListRoute.route) {
            AddressListScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToAddAddress = { navController.navigate(Route.AddAddressRoute.route) },
                onNavigateToEditAddress = { addressId ->
                    // TODO: Implement edit functionality in future PR
                }
            )
        }

        composable(Route.AddAddressRoute.route) {
            AddAddressScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}