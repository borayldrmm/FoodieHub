package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val imageResource: Int,
    val rating: Double?,
    val preparationTimeMinutes: Int?,
    val categoryId: Int,
    val isFavorite: Boolean = false
)