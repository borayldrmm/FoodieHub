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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override fun getUserOrders(userId: String): Flow<List<Order>> {
        return flow {
            orderDao.getUserOrders(userId).collect { orderEntities ->
                val orderWithItems = orderEntities.map { orderEntity ->
                    val items = orderDao.getOrderItems(orderEntity.id).first()
                    orderEntity.toDomain(items)
                }
                emit(orderWithItems)
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