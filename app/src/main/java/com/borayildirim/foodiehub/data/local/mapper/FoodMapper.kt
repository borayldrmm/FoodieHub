package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.FoodEntity
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.model.SideOption
import com.borayildirim.foodiehub.domain.model.Topping

/**
 * Converts FoodEntity to domain Food with UI-specific data
 *
 * Merges database entity with MockFoodData to create complete domain model.
 * Entity stores persistent data while MockFoodData provides UI strings and
 * customization options to avoid storing resource IDs in database.
 *
 * @param description String resource ID for short description
 * @param detailedDescription String resource ID for long description
 * @param availableToppings Customization options from MockFoodData
 * @param availableSideOptions Side dish options from MockFoodData
 */
fun FoodEntity.toDomain(
    description: Int? = null,
    detailedDescription: Int? = null,
    availableToppings: List<Topping> = emptyList(),
    availableSideOptions: List<SideOption> = emptyList()
): Food {
    return Food(
        id = this.id,
        name = this.name,
        price = this.price,
        imageResource = this.imageResource,
        rating = this.rating,
        preparationTimeMinutes = this.preparationTimeMinutes,
        categoryId = this.categoryId,
        description = description,
        detailedDescription = detailedDescription,
        isFavorite = this.isFavorite,
        availableToppings = availableToppings,
        availableSideOptions = availableSideOptions
    )
}

/**
 * Converts domain Food to entity for database storage
 *
 * Extracts only persistent data (core properties and favorite status).
 * UI-specific data (descriptions, customizations) are excluded as they
 * come from MockFoodData at runtime.
 */
fun Food.toEntity(): FoodEntity {
    return FoodEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        imageResource = this.imageResource,
        rating = this.rating,
        preparationTimeMinutes = this.preparationTimeMinutes,
        categoryId = this.categoryId,
        isFavorite = this.isFavorite
    )
}