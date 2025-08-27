package com.borayildirim.foodiehub.presentation.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.presentation.screens.HomeScreen
import com.borayildirim.foodiehub.presentation.theme.FoodieHubTheme
import com.borayildirim.foodiehub.presentation.theme.homeSubtitle
import com.borayildirim.foodiehub.presentation.theme.robotoTxt
import com.borayildirim.foodiehub.presentation.ui.components.home.FoodCardConstants.ASPECT_RATIO
import com.borayildirim.foodiehub.presentation.ui.components.home.FoodCardConstants.CARD_HEIGHT
import com.borayildirim.foodiehub.presentation.ui.components.home.FoodCardConstants.IMAGE_HEIGHT

object FoodCardConstants {
    val CARD_HEIGHT = 320.dp
    val IMAGE_HEIGHT = 140.dp
    const val ASPECT_RATIO = 4f / 3f
}

@Composable
fun HomeFoodsGrid(
    foods: List<Food>,
    onFoodClick: (Food) -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),    // <-- 2 Columns
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(foods) { food ->
            Card(
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CARD_HEIGHT)
                    .clickable { onFoodClick(food) }
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(ASPECT_RATIO)
                            .clip(RoundedCornerShape(10.dp))

                    ){
                        Image(
                            painter = painterResource(id = R.drawable.home_burgers_shadow),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .alpha(0.6f)
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp))
                        )
                        Image(
                            painter = painterResource(id = food.imageResource),
                            contentScale = ContentScale.Fit,
                            contentDescription = stringResource(id = R.string.burger_image),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .height(IMAGE_HEIGHT)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    )

                    food.description?.let {descriptionId ->
                        Text(
                            text = stringResource(descriptionId),
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.robotoTxt,
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                        )
                    }


                    Spacer(modifier = Modifier.weight(1f))


                    food.rating?.let {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 3.dp)

                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = stringResource(R.string.home_food_grid_rating),
                                    tint = Color(0xFFFFB617)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = food.rating.toString()
                                )
                            }

                            Icon(
                                imageVector = if (food.isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                                tint = if (food.isFavorite) Color.Red else Color.DarkGray,
                                contentDescription = stringResource(R.string.home_food_grid_add_favorite),
                                modifier = Modifier
                                    .clickable {
                                        onFavoriteClick(food.id)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

val mockFood = listOf(
    Food(
        id = 1,
        name = "Hamburger",
        description = R.string.hamburger_description,
        price = 299.99,
        imageResource = R.drawable.hamburger,
        rating = 4.5,
        preparationTimeMinutes = 25,
        categoryId = 2,
        isFavorite = true
    ),
    Food(
        id = 2,
        name = "Cheeseburger",
        description = R.string.cheeseburger_description,
        price = 349.99,
        imageResource = R.drawable.cheeseburger,
        rating = 4.5,
        preparationTimeMinutes = 25,
        categoryId = 2,
        isFavorite = false
    ),
    Food(
        id = 3,
        name = "Veggie Burger",
        description = R.string.veggie_burger_description,
        price = 279.99,
        imageResource = R.drawable.veggie_burger,
        rating = 4.0,
        preparationTimeMinutes = 30,
        categoryId = 2,
        isFavorite = true
    ),
    Food(
        id = 4,
        name = "Chicken Burger",
        description = R.string.chicken_burger_description,
        price = 249.99,
        imageResource = R.drawable.chicken_burger,
        rating = 4.5,
        preparationTimeMinutes = 23,
        categoryId = 2,
        isFavorite = false
    ),
    Food(
        id = 5,
        name = "Fried Chicken Burger",
        description = R.string.fried_chicken_burger_description,
        price = 349.99,
        imageResource = R.drawable.fried_chicken_burger,
        rating = 4.8,
        preparationTimeMinutes = 26,
        categoryId = 2,
        isFavorite = false
    ),
    Food(
        id = 6,
        name = "Texas Burger",
        description = R.string.texas_burger_description,
        price = 399.99,
        imageResource = R.drawable.texas_burger,
        rating = 4.8,
        preparationTimeMinutes = 26,
        categoryId = 2,
        isFavorite = false
    )
)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, locale = "tr")
@Composable
fun HomeFoodsGridPreview() {
    FoodieHubTheme {
        Surface {
            HomeFoodsGrid(mockFood, onFoodClick = { }, onFavoriteClick = { })
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FoodieHubTheme {
        HomeScreen(navController = rememberNavController())
    }
}