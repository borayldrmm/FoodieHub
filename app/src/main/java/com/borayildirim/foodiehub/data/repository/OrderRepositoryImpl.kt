package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.OrderDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.model.OrderStatus
import com.borayildirim.foodiehub.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository implementation for order operations with item joining
 *
 * Manages orders with reactive Flow queries that automatically join
 * order headers with their items. Uses Flow operators to merge data
 * from normalized storage for complete order representation.
 *
 * Architecture:
 * - Normalized storage: Orders and items in separate tables
 * - Runtime joins: Flow.combine merges related data
 * - Atomic writes: Transaction support for order creation
 */
class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override fun getUserOrders(userId: String): Flow<List<Order>> {
        return flow {
            orderDao.getUserOrders(userId).collect { orderEntities ->
                val ordersWithItems = orderEntities.map { orderEntity ->
                    val items = orderDao.getOrderItems(orderEntity.id).first()
                    orderEntity.toDomain(items)
                }
                emit(ordersWithItems)
            }
        }
    }

    override fun getOrderById(orderId: String): Flow<Order?> {
        return combine(
            orderDao.getOrderById(orderId),
            orderDao.getOrderItems(orderId)
        ) { orderEntity, itemEntities ->
            orderEntity?.toDomain(itemEntities)
        }
    }

    /**
     * Creates order with items in atomic transaction
     *
     * Ensures both order and items are inserted together - either
     * both succeed or both fail for data consistency.
     */
    override suspend fun createOrder(order: Order) {
        orderDao.insertOrderWithItems(
            order = order.toEntity(),
            items = order.items.map { it.toEntity() }
        )
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        orderDao.updateOrderStatus(orderId, status.name)
    }
}