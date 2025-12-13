package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for shopping cart item with business logic
 *
 * Combines food details with quantity and provides calculated total price.
 * Enforces business rule requiring positive quantities.
 *
 * @property food Complete food data including price and details
 * @property quantity Item count, must be > 0
 * @property itemId Unique identifier (typically UUID)
 * @property totalPrice Calculated property: price × quantity
 * @throws IllegalArgumentException if quantity ≤ 0
 */
data class CartItem(
    val food: Food,
    val quantity: Int = 1,
    val itemId: String,
) {
    init {
        require(quantity > 0) { "Quantity must be positive" }
    }

    val totalPrice: Double get() = food.price * quantity
}