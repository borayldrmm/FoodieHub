package com.borayildirim.foodiehub.domain.model

import com.borayildirim.foodiehub.R

object MockFoodData {

    private val foodToppings = listOf(
        Topping(1, "Ketçap", false, R.drawable.ketchup),
        Topping(2, "Mayonez", false, R.drawable.mayonnaise),
        Topping(3, "Hardal", false, R.drawable.mustard),
        Topping(4, "BBQ Sos", false, R.drawable.bbq_sauce),
        Topping(5, "Domates Sos", false, R.drawable.tomatoes_sauce),
        Topping(6, "Acı Sos", false, R.drawable.hot_chili_sauce),
        Topping(7, "Domates", false, R.drawable.tomatoes),
        Topping(8, "Soğan", false, R.drawable.onion),
        Topping(9, "Turşu", false, R.drawable.pickles),
        Topping(10, "Bacon", false, R.drawable.bacon),
        Topping(11, "Peynir", false, R.drawable.cheese),
        Topping(12, "Mantar", false, R.drawable.mushroom),
        Topping(13, "Avokado", false, R.drawable.avocado),
        Topping(14,"Marul", false, R.drawable.salad),
        Topping(15,"Biber", false, R.drawable.sweet_pepper),
        Topping(16,"Zeytin", false, R.drawable.olive),
        Topping(17,"Mısır", false, R.drawable.corn),
        )

    private val sideOptions = listOf(
        SideOption(52, "Chips", 15.0, R.drawable.fries),
        SideOption(53, "Coleslaw", 12.0, R.drawable.coleslaw),
        SideOption(54, "Soğan Halkası", 20.0, R.drawable.onion_rings),
        SideOption(55, "Mozarella", 20.0, R.drawable.mozarella),
    )
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Ketçap",
                        "Mayonez",
                        "Turşu",
                        "Soğan",
                        "Domates",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Ketçap",
                        "Mayonez",
                        "Turşu",
                        "Soğan",
                        "Peynir",
                        "Domates",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Turşu",
                        "Soğan",
                        "Domates",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Mayonez",
                        "Bacon",
                        "Soğan",
                        "Domates",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Ketçap",
                        "Mayonez",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Hardal",
                        "BBQ Sos",
                        "Acı Sos",
                        "Peynir" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Domates",
                        "Biber",
                        "Domates Sos" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Zeytin",
                        "Mantar",
                        "Biber",
                        "Domates",
                        "Domates Sos" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Biber",
                        "Domates",
                        "Domates Sos" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Biber",
                        "Domates",
                        "Mantar",
                        "Mısır",
                        "Zeytin",
                        "Domates Sos" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Biber",
                        "Domates",
                        "Peynir",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Avokado",
                        "Biber",
                        "Domates",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Mısır",
                        "Zeytin",
                        "Acı Sos",
                        "Domates",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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
                isFavorite = false,
                availableSideOptions = sideOptions,
                availableToppings = foodToppings.map {
                    when (it.name) {
                        "Soğan",
                        "Domates",
                        "Turşu",
                        "Mısır",
                        "Marul" -> it.copy(isIncluded = true)
                        else -> it.copy(isIncluded = it.isIncluded)
                    }
                }
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