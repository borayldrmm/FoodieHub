package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.model.OrderStatus
import com.borayildirim.foodiehub.domain.repository.OrderRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(orderId: String, status: OrderStatus) {
        orderRepository.updateOrderStatus(orderId, status)
    }
}