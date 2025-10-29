package com.borayildirim.foodiehub.domain.model

data class Order(
    val id: String,
    val userId: String,
    val orderDate: Long,
    val totalAmount: Double,
    val deliveryFee: Double,
    val tax: Double,
    val status: OrderStatus,
    val deliveryAddress: String,
    val estimatedDeliveryTime: String,
    val items: List<OrderItem> = emptyList()
)

enum class OrderStatus {
    PENDING,
    PREPARING,
    ON_THE_WAY,
    DELIVERED,
    CANCELLED
}