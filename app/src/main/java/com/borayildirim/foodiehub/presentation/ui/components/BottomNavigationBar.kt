package com.borayildirim.foodiehub.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.presentation.navigation.Route


/**
 * Custom bottom navigation bar with integrated floating action button
 *
 * Features:
 * - Responsive design based on screen width
 * - Custom notched shape for FAB integration
 * - Dynamic icon states (filled/outlined) based on selection
 * - Cart badge showing item count
 * - Material3 design system compliance
 *
 * @param navController Navigation controller for routing between screens
 * @param cartItemCount Number of items in cart for badge display
 * @param onFabClick Callback for FAB button clicks
 */
@Composable
fun BottomNavigationBar(
    navController: NavController,
    cartItemCount: Int = 0,  // ← YENİ PARAMETRE
    onFabClick: () -> Unit = {}
) {
    val currentDestination = navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination.value?.destination?.route

    // Dynamic icon selection: filled when selected, outlined when inactive
    val homeIcon = if (currentRoute?.startsWith("home") == true) Icons.Filled.Home else Icons.Outlined.Home
    val favIcon = if (currentRoute == Route.Favorites.route) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    val cartIcon = if (currentRoute == Route.Cart.route) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart
    val profileIcon = if (currentRoute == Route.Profile.route) Icons.Filled.Person else Icons.Outlined.Person

    val screenWidth = LocalConfiguration.current.screenWidthDp

    // Calculate responsive dimensions based on screen width
    val fabSize = remember(screenWidth) { (screenWidth * 0.19f).dp }
    val barHeight = maxOf(72.dp, fabSize * 0.95f)
    val centerGap = fabSize + (fabSize * 0.40f)

    val notchedShape = remember(fabSize) {
        BottomBarNotchedShape(
            notchWidthDp = fabSize * 1.80f,
            notchDepthDp = fabSize * 0.58f,
            shoulderEase = 0.25f,
            topCornerRadiusDp = 28.dp
        )
    }

    // Main container with transparent background
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Bottom navigation bar with clipped shape
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .clip(notchedShape)
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationBarItem(
                    selected = currentRoute?.startsWith("home") == true,
                    onClick = { navController.navigate(Route.Home.route) },
                    icon = { Icon(homeIcon, contentDescription = "Home", tint = Color.White) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = currentRoute == Route.Favorites.route,
                    onClick = { navController.navigate(Route.Favorites.route) },
                    icon = { Icon(favIcon, contentDescription = "Favorites", tint = Color.White) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent
                    )
                )

                // Spacer for FAB notch
                Spacer(Modifier.width(centerGap))

                NavigationBarItem(
                    selected = currentRoute == Route.Cart.route,
                    onClick = { navController.navigate(Route.Cart.route) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge(
                                        containerColor = Color.White,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ) {
                                        Text(
                                            text = cartItemCount.toString(),
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(cartIcon, contentDescription = "Cart", tint = Color.White)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = currentRoute == Route.Profile.route,
                    onClick = { navController.navigate(Route.Profile.route) },
                    icon = { Icon(profileIcon, contentDescription = "Profile", tint = Color.White) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.7f),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onFabClick,
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(fabSize)
                .offset(y = (-fabSize * 0.7f))
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(R.string.bottom_nav_new_order),
                modifier = Modifier.size(fabSize * 0.5f),
                tint = Color.White
            )
        }
    }
}