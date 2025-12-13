package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for order header information
 *
 * Stores order metadata without item details for normalized storage.
 * Order items are stored separately in OrderItemEntity with foreign key.
 *
 * @property id Unique order identifier (UUID)
 * @property userId Order owner's user ID
 * @property orderDate Timestamp in milliseconds since epoch
 * @property totalAmount Final total including tax and delivery
 * @property deliveryFee Delivery charge amount
 * @property tax Tax amount calculated from subtotal
 * @property status Order status as string (PENDING, PREPARING, etc.)
 * @property deliveryAddress Full delivery address string
 * @property estimatedDeliveryTime Format "15 - 30dk"
 */
@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val orderDate: Long,
    val totalAmount: Double,
    val deliveryFee: Double,
    val tax: Double,
    val status: String,
    val deliveryAddress: String,
    val estimatedDeliveryTime: String
)