package com.borayildirim.foodiehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.borayildirim.foodiehub.domain.usecase.cart.GetCartItemCountUseCase
import com.borayildirim.foodiehub.presentation.navigation.NavGraph
import com.borayildirim.foodiehub.presentation.navigation.Route
import com.borayildirim.foodiehub.presentation.theme.FoodieHubTheme
import com.borayildirim.foodiehub.presentation.ui.components.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Main entry point activity for the FoodieHub application
 *
 * Manages primary navigation structure and global UI state including
 * cart badge count visible across all screens with bottom navigation.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodieHubTheme {
                val mainScreenRoutes = listOf(
                    Route.Home.route,
                    Route.Favorites.route,
                )
                val navController = rememberNavController()
                val currentDestination = navController.currentBackStackEntryAsState()
                val currentRoute = currentDestination.value?.destination?.route
                val showBottomBar = remember(currentRoute) {
                    currentRoute?.startsWith("home") == true ||
                            mainScreenRoutes.contains(currentRoute)
                }

                val cartItemCount by viewModel.cartItemCount.collectAsState()

                Scaffold(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    contentWindowInsets = WindowInsets(0.dp),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(
                                navController = navController,
                                cartItemCount = cartItemCount,  // ← EKLENDI
                                onFabClick = {
                                    navController.navigate(Route.Home.createRoute(openQuickOrder = true)) {
                                        popUpTo(Route.Home.route) { inclusive = true }
                                    }
                                }
                            )
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

/**
 * ViewModel for MainActivity managing global app state
 *
 * Observes cart item count for badge display on bottom navigation.
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCartItemCountUseCase: GetCartItemCountUseCase
) : ViewModel() {

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount = _cartItemCount.asStateFlow()

    init {
        loadCartCount()
    }

    private fun loadCartCount() {
        viewModelScope.launch {
            getCartItemCountUseCase().collect { count ->
                _cartItemCount.value = count
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