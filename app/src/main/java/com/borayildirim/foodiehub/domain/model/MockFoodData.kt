package com.borayildirim.foodiehub.domain.model

import com.borayildirim.foodiehub.R

object MockFoodData {
    fun getBurgers(): List<Food> {
        return listOf(
            Food(
                id = 1,
                name = "Hamburger",
                description = R.string.hamburger_description,
                price = 299.99,
                imageResource = R.drawable.hamburger,
                rating = 4.5,
                preparationTimeMinutes = 25,
                categoryId = 2,
                isFavorite = false
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
                isFavorite = false
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
    }

    fun getPizza(): List<Food> {
        return listOf(
            Food(
                id = 7,
                name = "Pepperoni Pizza",
                description = R.string.pepperoni_pizza_description,
                price = 299.99,
                imageResource = R.drawable.pizza_pepperoni,
                rating = 4.2,
                preparationTimeMinutes = 30,
                categoryId = 3,
                isFavorite = false
            ),
            Food(
                id = 8,
                name = "Vegetable Pizza",
                description = R.string.vegetable_pizza_description,
                price = 249.99,
                imageResource = R.drawable.pizza_vegetable,
                rating = 3.9,
                preparationTimeMinutes = 28,
                categoryId = 3,
                isFavorite = false
            ),
            Food(
                id = 9,
                name = "Margherita Pizza",
                description = R.string.margherita_pizza_description,
                price = 349.99,
                imageResource = R.drawable.pizza_margherita,
                rating = 4.3,
                preparationTimeMinutes = 25,
                categoryId = 3,
                isFavorite = false
            ),
            Food(
                id = 10,
                name = "Karışık Pizza",
                description = R.string.mixed_pizza_description,
                price = 399.99,
                imageResource = R.drawable.pizza_mixed,
                rating = 4.9,
                preparationTimeMinutes = 35,
                categoryId = 3,
                isFavorite = false
            ),
        )
    }

    fun getSalad(): List<Food> {
        return listOf(
            Food(
                id = 11,
                name = "Akdeniz Tavuk Salatası",
                description = R.string.mediterranean_chicken_salad_description,
                price = 299.99,
                imageResource = R.drawable.mediterranean_chicken_salad,
                rating = 4.2,
                preparationTimeMinutes = 30,
                categoryId = 4,
                isFavorite = false
            ),
            Food(
                id = 12,
                name = "Avokadolu Tavuk Salatası",
                description = R.string.avocado_chicken_salad_description,
                price = 249.99,
                imageResource = R.drawable.avacado_chicken_salad,
                rating = 3.9,
                preparationTimeMinutes = 28,
                categoryId = 4,
                isFavorite = false
            ),
            Food(
                id = 13,
                name = "Baharatlı Tavuklu Kinoalı Salata",
                description = R.string.spicy_chicken_quinoa_salad_description,
                price = 349.99,
                imageResource = R.drawable.spicy_chicken_quinoa_salad,
                rating = 4.3,
                preparationTimeMinutes = 25,
                categoryId = 4,
                isFavorite = false
            ),
            Food(
                id = 14,
                name = "Yumurtalı Akdeniz Salatası",
                description = R.string.egg_mediterranean_salad_description,
                price = 399.99,
                imageResource = R.drawable.egg_mediterranean_salad,
                rating = 4.9,
                preparationTimeMinutes = 35,
                categoryId = 4,
                isFavorite = false
            ),
        )
    }

    fun getDrinks(): List<Food> {
        return listOf(
            Food(
                id = 15,
                name = "Coca-Cola",
                price = 30.00,
                imageResource = R.drawable.coca_cola,
                categoryId = 5,
                isFavorite = false
            ),
            Food(
                id = 16,
                name = "Fanta",
                price = 30.00,
                imageResource = R.drawable.fanta,
                categoryId = 5,
                isFavorite = false
            ),
            Food(
                id = 17,
                name = "Sprite",
                price = 30.00,
                imageResource = R.drawable.sprite,
                categoryId = 5,
                isFavorite = false
            ),
            Food(
                id = 18,
                name = "Ayran",
                price = 20.00,
                imageResource = R.drawable.ayran,
                categoryId = 5,
                isFavorite = false
            ),
            Food(
                id = 19,
                name = "Su",
                price = 15.00,
                imageResource = R.drawable.water,
                categoryId = 5,
                isFavorite = false
            ),
            Food(
                id = 20,
                name = "Soda",
                price = 20.00,
                imageResource = R.drawable.mineral_water,
                categoryId = 5,
                isFavorite = false
            ),
        )
    }
}