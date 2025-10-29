package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getUserOrders(userId: String): Flow<List<Order>>
    fun getOrderById(orderId: String): Flow<Order?>
    suspend fun createOrder(order: Order)
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus)
}