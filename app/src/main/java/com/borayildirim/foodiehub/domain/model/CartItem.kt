package com.borayildirim.foodiehub.domain.model


data class CartItem (
    val food: Food,
    val quantity: Int = 1,
    val itemId: String,
) {
    init {
        require(quantity > 0) { "Quantity must be positive" }
    }

    val totalPrice: Double get() = food.price * quantity
}