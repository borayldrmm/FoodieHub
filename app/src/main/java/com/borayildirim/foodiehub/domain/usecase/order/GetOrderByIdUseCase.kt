package com.borayildirim.foodiehub.domain.usecase.order

import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving order by ID with reactive updates
 */
class GetOrderByIdUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(orderId: String): Flow<Order?> {
        return orderRepository.getOrderById(orderId)
    }
}