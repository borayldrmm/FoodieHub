package com.borayildirim.foodiehub.domain.model

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