package com.borayildirim.foodiehub.domain.model

data class Topping(
    val id: Int,
    val name: String,
    val isIncluded: Boolean = false,
    val imageRes: Int
)