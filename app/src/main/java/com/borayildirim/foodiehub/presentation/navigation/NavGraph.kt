package com.borayildirim.foodiehub.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.borayildirim.foodiehub.presentation.screens.home.HomeScreen
import com.borayildirim.foodiehub.presentation.screens.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Splash.route) { SplashScreen(navController) }
        composable(Route.Home.route) { HomeScreen(navController) }
    }
}