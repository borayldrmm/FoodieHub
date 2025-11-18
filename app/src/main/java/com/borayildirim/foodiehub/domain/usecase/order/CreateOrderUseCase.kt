package com.borayildirim.foodiehub.domain.usecase.order

import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(order: Order) {
        orderRepository.createOrder(order)
    }
}