package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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