package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for individual order line item
 *
 * Represents single food item in order with quantity, customizations,
 * and pricing snapshot at order time. Preserves food details even if
 * menu changes later.
 *
 * @property id Unique line item identifier (UUID)
 * @property orderId Parent order identifier
 * @property productId Food ID at order time (historical reference)
 * @property productName Food name snapshot
 * @property productImage Image resource ID snapshot
 * @property quantity Number of this item ordered
 * @property price Unit price at order time
 * @property spicyLevel Selected spice level (0.0 to 1.0)
 * @property selectedToppings List of topping names
 * @property selectedSides List of side option names
 */
data class OrderItem(
    val id: String,
    val orderId: String,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val quantity: Int,
    val price: Double,
    val spicyLevel: Float,
    val selectedToppings: List<String>,
    val selectedSides: List<String>
)