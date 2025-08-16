package com.borayildirim.foodiehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.borayildirim.foodiehub.presentation.navigation.NavGraph
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.theme.FoodieHubTheme
import com.borayildirim.foodiehub.presentation.ui.components.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint


/**
 * Main entry point activity for the FoodieHub application.
 *
 * Manages the primary navigation structure and UI state for the entire app.
 * Implements conditional bottom navigation visibility based on current route
 * and provides the main navigation graph for screen transitions.
 *
 * Features:
 * - Conditional bottom navigation (hidden on splash screen)
 * - Navigation state management with Jetpack Navigation
 * - Material3 theme integration
 * - Edge-to-edge display support
 *
 * @since 1.0.0
 * @author FoodieHub Team
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodieHubTheme {
                val navController = rememberNavController()
                val currentDestination = navController.currentBackStackEntryAsState()
                val currentRoute = currentDestination.value?.destination?.route
                val showBottomBar = remember(currentRoute) {
                    currentRoute != Route.Splash.route
                }

                /**
                 * Conditional UI rendering: Bottom navigation is hidden during splash screen
                 * to provide distraction-free app loading experience, then shown for main app navigation.
                 */
                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavGraph(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodieHubTheme {

    }
}