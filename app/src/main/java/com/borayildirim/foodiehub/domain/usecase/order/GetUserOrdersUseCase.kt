package com.borayildirim.foodiehub.domain.usecase.order

import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(userId: String): Flow<List<Order>> {
        return orderRepository.getUserOrders(userId)
    }
}