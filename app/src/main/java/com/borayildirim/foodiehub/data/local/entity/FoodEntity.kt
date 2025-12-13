package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for food menu items with favorite tracking
 *
 * Stores core food data in normalized storage. Additional UI-specific
 * data (descriptions, customizations) are provided by MockFoodData at
 * runtime to avoid database bloat with resource IDs.
 *
 * @property id Primary key (food ID)
 * @property name Food name for display and search
 * @property price Unit price in local currency
 * @property imageResource Drawable resource ID
 * @property rating User rating (0.0 to 5.0) or null if unrated
 * @property preparationTimeMinutes Estimated cooking time
 * @property categoryId Foreign key to category (1=All, 2=Burgers, etc.)
 * @property isFavorite User's favorite status, persists across sessions
 */
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