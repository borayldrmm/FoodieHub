package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val itemId: String,
    val foodId: Int,
    val quantity: Int
)