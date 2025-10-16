package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.FoodEntity
import com.borayildirim.foodiehub.domain.model.Food
import com.borayildirim.foodiehub.domain.model.SideOption
import com.borayildirim.foodiehub.domain.model.Topping

fun FoodEntity.toDomain(
    description: Int? = null,
    detailedDescription: Int? = null,
    isFavorite: Boolean = false,
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
        isFavorite = isFavorite,
        availableToppings = availableToppings,
        availableSideOptions = availableSideOptions
    )
}