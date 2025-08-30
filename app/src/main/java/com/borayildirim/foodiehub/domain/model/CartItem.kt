package com.borayildirim.foodiehub.domain.model

import java.util.UUID

data class CartItem (
    val food: Food,
    val quantity: Int = 1,
    val itemId: UUID,
) {
    init {
        require(quantity > 0) { "Quantity must be positive" }
    }

    val totalPrice: Double get() = food.price * quantity
}