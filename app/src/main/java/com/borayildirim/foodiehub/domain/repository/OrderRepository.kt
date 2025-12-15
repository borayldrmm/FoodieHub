package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Order
import com.borayildirim.foodiehub.domain.model.OrderStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for order operations
 *
 * Provides order management with reactive Flow queries that automatically
 * join order headers with their items. Ensures atomic order creation
 * with transaction support for data consistency.
 *
 * Architecture:
 * - Reactive queries: Flow-based for real-time order updates
 * - Normalized storage: Orders and items in separate tables
 * - Atomic operations: Transaction support for order creation
 *
 * Business rules:
 * - Orders include complete item details with customizations
 * - Order status progresses: PENDING → PREPARING → ON_THE_WAY → DELIVERED
 * - Item prices are snapshots at order time (historical pricing)
 */
interface OrderRepository {
    /**
     * Retrieves all orders for user with reactive updates
     *
     * Returns orders sorted by date (newest first) with complete
     * item details including customizations and pricing.
     *
     * @param userId User identifier
     * @return Flow emitting order list with items on any changes
     */
    fun getUserOrders(userId: String): Flow<List<Order>>

    /**
     * Retrieves specific order by ID with reactive updates
     *
     * Automatically joins order with its items for complete representation.
     *
     * @param orderId Order identifier
     * @return Flow emitting order with items, or null if not found
     */
    fun getOrderById(orderId: String): Flow<Order?>

    /**
     * Creates new order with items in atomic transaction
     *
     * Inserts order header and all items atomically - either both
     * succeed or both fail for data consistency. Order must include
     * at least one item.
     *
     * @param order Complete order with items to create
     * @throws Exception if transaction fails
     */
    suspend fun createOrder(order: Order)

    /**
     * Updates order status for lifecycle tracking
     *
     * Used for status progression as order moves through fulfillment.
     * Status changes trigger reactive updates to order queries.
     *
     * @param orderId Order identifier
     * @param status New order status
     */
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus)
}