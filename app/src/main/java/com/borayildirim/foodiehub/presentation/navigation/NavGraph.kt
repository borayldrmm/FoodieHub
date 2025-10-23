package com.borayildirim.foodiehub.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.borayildirim.foodiehub.presentation.screens.CartScreen
import com.borayildirim.foodiehub.presentation.screens.CustomizationScreen
import com.borayildirim.foodiehub.presentation.screens.FavoritesScreen
import com.borayildirim.foodiehub.presentation.screens.FoodDetailScreen
import com.borayildirim.foodiehub.presentation.screens.HomeScreen
import com.borayildirim.foodiehub.presentation.screens.LoginScreen
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

        composable(Route.Home.route) { HomeScreen(navController) }

        // FavoritesScreen
        composable(Route.Favorites.route) {
            FavoritesScreen(navController)
        }

        // Cart route
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

        // ProfileScreen
        composable(Route.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Route.Payment.route) {
            PaymentScreen(navController = navController)
        }
    }
}