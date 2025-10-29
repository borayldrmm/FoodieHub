package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.OrderEntity
import com.borayildirim.foodiehub.data.local.entity.OrderItemEntity
import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.model.OrderItem
import com.borayildirim.foodiehub.domain.model.OrderStatus

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