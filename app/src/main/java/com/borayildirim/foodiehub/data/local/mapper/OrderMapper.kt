package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.OrderEntity
import com.borayildirim.foodiehub.data.local.entity.OrderItemEntity
import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.model.OrderItem
import com.borayildirim.foodiehub.domain.model.OrderStatus

/**
 * Converts OrderEntity to domain Order with order items
 *
 * Joins order header with items for complete order representation.
 *
 * @param items Order items from separate table (normalized storage)
 */
fun OrderEntity.toDomain(items: List<OrderItemEntity> = emptyList()): Order {
    return Order(
        id = id,
        userId = userId,
        orderDate = orderDate,
        totalAmount = totalAmount,
        deliveryFee = deliveryFee,
        tax = tax,
        status = OrderStatus.valueOf(status),
        deliveryAddress = deliveryAddress,
        estimatedDeliveryTime = estimatedDeliveryTime,
        items = items.map { it.toDomain() }
    )
}

/**
 * Converts domain Order to entity for database storage
 *
 * Note: Items are not included - they must be inserted separately
 * via OrderDao.insertOrderItems() or insertOrderWithItems() transaction.
 */
fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        userId = userId,
        orderDate = orderDate,
        totalAmount = totalAmount,
        deliveryFee = deliveryFee,
        tax = tax,
        status = status.name,
        deliveryAddress = deliveryAddress,
        estimatedDeliveryTime = estimatedDeliveryTime
    )
}

/**
 * Converts OrderItemEntity to domain OrderItem
 *
 * Parses comma-separated strings for toppings and sides.
 */
fun OrderItemEntity.toDomain(): OrderItem {
    return OrderItem(
        id = id,
        orderId = orderId,
        productId = productId,
        productName = productName,
        productImage = productImage,
        quantity = quantity,
        price = price,
        spicyLevel = spicyLevel,
        selectedToppings = selectedToppings.split(",").filter { it.isNotBlank() },
        selectedSides = selectedSides.split(",").filter { it.isNotBlank() }
    )
}

/**
 * Converts domain OrderItem to entity for database storage
 *
 * Serializes lists to comma-separated strings for simple storage.
 */
fun OrderItem.toEntity(): OrderItemEntity {
    return OrderItemEntity(
        id = id,
        orderId = orderId,
        productId = productId,
        productName = productName,
        productImage = productImage,
        quantity = quantity,
        price = price,
        spicyLevel = spicyLevel,
        selectedToppings = selectedToppings.joinToString(","),
        selectedSides = selectedSides.joinToString(",")
    )
}