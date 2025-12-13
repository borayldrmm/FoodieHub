package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for complete order with items
 *
 * Represents user order with all details including items, pricing,
 * and delivery information. Combines order header with line items
 * for complete order representation.
 *
 * @property id Unique order identifier (UUID)
 * @property userId Order owner's user ID
 * @property orderDate Timestamp in milliseconds since epoch
 * @property totalAmount Final total including tax and delivery
 * @property deliveryFee Delivery charge amount
 * @property tax Tax amount calculated from subtotal
 * @property status Current order status
 * @property deliveryAddress Full delivery address
 * @property estimatedDeliveryTime Format "15 - 30dk"
 * @property items List of ordered food items with customizations
 */
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

/**
 * Order lifecycle status enum
 *
 * Represents order progression from placement to completion.
 * Used for status tracking and UI state display.
 */
enum class OrderStatus {
    /** Order placed, awaiting restaurant confirmation */
    PENDING,

    /** Restaurant preparing food */
    PREPARING,

    /** Driver en route to delivery address */
    ON_THE_WAY,

    /** Order successfully delivered */
    DELIVERED,

    /** Order cancelled by user or restaurant */
    CANCELLED
}