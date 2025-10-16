package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.CartItemEntity
import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.Food

// Entity --> Domain
fun CartItemEntity.toDomain(food: Food): CartItem {
    return CartItem(
        food = food,
        quantity = this.quantity,
        itemId = this.itemId
    )
}

// Domain --> Entity
fun CartItem.toEntity(): CartItemEntity {
    return CartItemEntity(
        itemId = this.itemId,
        foodId = this.food.id,
        quantity = this.quantity
    )
}