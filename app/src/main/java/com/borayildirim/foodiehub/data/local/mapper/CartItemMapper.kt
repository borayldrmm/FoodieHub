package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.CartItemEntity
import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food

/**
 * Converts CartItemEntity to domain CartItem with full food details
 *
 * Requires Food object since entity stores only foodId for normalization.
 *
 * @param food Complete food data joined from foods table
 */
fun CartItemEntity.toDomain(food: Food): CartItem {
    return CartItem(
        food = food,
        quantity = this.quantity,
        itemId = this.itemId
    )
}

/**
 * Converts domain CartItem to entity for database storage
 *
 * Extracts only essential data (foodId, quantity) for normalized storage.
 */
fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        itemId = this.itemId,
        foodId = this.food.id,
        quantity = this.quantity
    )
}