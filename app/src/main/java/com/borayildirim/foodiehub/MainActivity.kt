package com.borayildirim.foodiehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.borayildirim.foodiehub.presentation.navigation.NavGraph
import com.borayildirim.foodiehub.presentation.theme.FoodieHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodieHubTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)


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