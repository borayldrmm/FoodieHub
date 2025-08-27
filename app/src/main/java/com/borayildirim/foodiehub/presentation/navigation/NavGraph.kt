package com.borayildirim.foodiehub.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.borayildirim.foodiehub.presentation.screens.FoodDetailScreen
import com.borayildirim.foodiehub.presentation.screens.HomeScreen
import com.borayildirim.foodiehub.presentation.screens.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Splash.route) { SplashScreen(navController) }
        composable(Route.Add.route) { Text("Add Screen") }
        composable(Route.Home.route) { HomeScreen(navController) }
        composable(Route.Cart.route) { Text("Cart Screen") }
        composable(Route.Profile.route) { Text("Profile Screen") }
        composable(Route.Favorites.route) { Text("Favorites Screen") }

        composable(
            route = Route.FoodDetail.route,
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
            FoodDetailScreen(navController, foodId)
        }

    }
}