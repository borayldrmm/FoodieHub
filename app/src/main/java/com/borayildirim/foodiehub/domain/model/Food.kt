package com.borayildirim.foodiehub.domain.model

data class Food(
    val id: Int,
    val name: String,
    val description: Int? = null,
    val price: Double,
    val imageResource: Int,
    val rating: Double? = null,
    val preparationTimeMinutes: Int? = null,
    val categoryId: Int,
    val isFavorite: Boolean
)