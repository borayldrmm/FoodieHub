package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for order cost breakdown
 *
 * Provides pre-checkout summary with calculated total.
 * Used in payment screen to show cost components before order confirmation.
 *
 * @property subtotal Sum of all item prices before fees
 * @property tax Calculated tax amount
 * @property deliveryFee Delivery service charge
 * @property estimatedDeliveryTime Format "15 - 30dk"
 * @property total Calculated property: subtotal + tax + deliveryFee
 */
data class OrderSummary(
    val subtotal: Double,
    val tax: Double,
    val deliveryFee: Double,
    val estimatedDeliveryTime: String = "15 - 30dk"
) {
    val total: Double
        get() = subtotal + tax + deliveryFee
}