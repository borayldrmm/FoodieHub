package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for cart items with minimal data storage
 *
 * Stores only essential cart data (foodId, quantity). Full food details
 * are joined from foods table at runtime for memory efficiency.
 *
 * @property itemId Unique identifier for cart entry (UUID)
 * @property foodId Foreign key reference to foods table
 * @property quantity Item count, must be positive
 */
@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val itemId: String,
    val foodId: Int,
    val quantity: Int
)