package com.borayildirim.foodiehub.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.borayildirim.foodiehub.presentation.navigation.Route


/**
 * Custom bottom navigation bar with integrated floating action button.
 *
 * Features:
 * - Responsive design based on screen width
 * - Custom notched shape for FAB integration
 * - Dynamic icon states (filled/outlined) based on selection
 * - Material3 design system compliance
 *
 * @param navController Navigation controller for routing between screens
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentDestination = navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination.value?.destination?.route

    // Dynamic icon selection: filled when selected, outlined when inactive
    // Provides clear visual feedback for current navigation state
    val homeIcon = if (currentRoute == Route.Home.route) Icons.Filled.Home else Icons.Outlined.Home
    val favIcon = if (currentRoute == Route.Favorites.route) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    val cartIcon = if (currentRoute == Route.Cart.route) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart
    val profileIcon = if (currentRoute == Route.Profile.route) Icons.Filled.Person else Icons.Outlined.Person
    val screenWidth = LocalConfiguration.current.screenWidthDp

    // Calculate responsive dimensions based on screen width
    val fabSize = remember(screenWidth) { (screenWidth * 0.19f).dp }    // FAB size: 19% of screen width for optimal touch target
    val barHeight = maxOf(72.dp, fabSize * 0.95f)
    val centerGap = fabSize + (fabSize * 0.40f)                         // Center gap calculation: FAB width + 40% padding for visual balance

    val notchedShape = remember(fabSize) {
        BottomBarNotchedShape(
            notchWidthDp = fabSize * 1.80f,
            notchDepthDp = fabSize * 0.58f,
            shoulderEase = 0.25f,
            topCornerRadiusDp = 28.dp
        )
    }

    Box() {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            tonalElevation = 8.dp,
            shape = notchedShape,
            modifier = Modifier.height(barHeight)
        ) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                tonalElevation = 0.dp,
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                NavigationBarItem(
                    selected = currentRoute == Route.Home.route,
                    onClick = { navController.navigate(Route.Home.route) },
                    icon = { Icon(homeIcon, contentDescription = "Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,  // Selected icon color
                        unselectedIconColor = Color.White, // Unselected icon color
                        indicatorColor = Color.Transparent  // ← Hide INDICATOR
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == Route.Favorites.route,
                    onClick = { navController.navigate(Route.Favorites.route)},
                    icon = { Icon(favIcon, contentDescription = "Favorites") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,  // Selected icon color
                        unselectedIconColor = Color.White, // Unselected icon color
                        indicatorColor = Color.Transparent  // ← Hide INDICATOR
                    )
                )

                Spacer(Modifier.width(centerGap))           // FAB + notch için oransal boşluk

                NavigationBarItem(
                    selected = currentRoute == Route.Cart.route,
                    onClick = { navController.navigate(Route.Cart.route) },
                    icon = { Icon(cartIcon, contentDescription = "Cart") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,  // Selected icon color
                        unselectedIconColor = Color.White, // Unselected icon color
                        indicatorColor = Color.Transparent  // ← Hide INDICATOR
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == Route.Profile.route,
                    onClick = { navController.navigate(Route.Profile.route) },
                    icon = { Icon(profileIcon, contentDescription = "Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,  // Selected icon color
                        unselectedIconColor = Color.White, // Unselected icon color
                        indicatorColor = Color.Transparent  // ← Hide INDICATOR
                    )
                )
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Route.Add.route) },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(fabSize)
                .offset(y = (-fabSize * 0.7f))
        ) {
            Icon(Icons.Filled.Add,
                contentDescription = "New Order",
                modifier = Modifier.size(fabSize * 0.5f))
        }
    }
}