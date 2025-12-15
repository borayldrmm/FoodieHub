package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for food menu item with complete details
 *
 * Combines persistent data from database with UI-specific content
 * from MockFoodData. Includes customization options for food detail
 * screen and order placement.
 *
 * Data sources:
 * - Database: id, name, price, imageResource, rating, categoryId, isFavorite
 * - MockFoodData: description, detailedDescription, toppings, sides
 *
 * @property id Unique food identifier
 * @property name Display name
 * @property description String resource ID for short description
 * @property detailedDescription String resource ID for detailed info
 * @property price Unit price in local currency
 * @property imageResource Drawable resource ID
 * @property rating User rating (0.0 to 5.0) or null
 * @property preparationTimeMinutes Estimated cooking time
 * @property categoryId Category identifier (1=All, 2=Burgers, etc.)
 * @property isFavorite User's favorite status
 * @property availableToppings Customization options with prices
 * @property availableSideOptions Side dish options with prices
 */
data class Food(
    val id: Int,
    val name: String,
    val description: Int? = null,
    val detailedDescription: Int? = null,
    val price: Double,
    val imageResource: Int,
    val rating: Double? = null,
    val preparationTimeMinutes: Int? = null,
    val categoryId: Int,
    val isFavorite: Boolean,
    val availableToppings: List<Topping> = emptyList(),
    val availableSideOptions: List<SideOption> = emptyList()
)