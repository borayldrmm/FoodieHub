package com.borayildirim.foodiehub.domain.model

data class OrderSummary(
    val subtotal: Double,
    val tax: Double,
    val deliveryFee: Double,
    val estimatedDeliveryTime: String = "15 - 30dk"
) {
    val total: Double
        get() = subtotal + tax + deliveryFee
}