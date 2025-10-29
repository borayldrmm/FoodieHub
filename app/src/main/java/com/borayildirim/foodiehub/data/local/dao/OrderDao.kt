package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.borayildirim.foodiehub.data.local.entity.OrderEntity
import com.borayildirim.foodiehub.data.local.entity.OrderItemEntity
import kotlinx.coroutines.flow.Flow


/**
 * Data Access Object for Order operations
 *
 * Provides Flow-based queries for reactive data updates
 * and Transaction support for atomic operations
 */
@Dao
interface OrderDao {

    /**
     * Get all orders for a specific user, sorted by date (newest first)
     *
     * @param userId User ID to get orders for
     * @return Flow of user's orders
     */

    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY orderDate DESC")
    fun getUserOrders(userId: String): Flow<List<OrderEntity>>

    /**
     * Get order by ID
     *
     * @param orderId Order ID
     * @return Flow of order or null if not found
     */

    @Query("SELECT * FROM orders WHERE id = :orderId")
    fun getOrderById(orderId: String): Flow<OrderEntity?>

    /**
     * Get all items for a specific order
     *
     * @param orderId Order ID
     * @return Flow of order items
     */

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getOrderItems(orderId: String): Flow<List<OrderItemEntity>>

    /**
     * Insert order into database
     *
     * @param order Order to insert
     * @return Row ID of inserted order
     */
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    /**
     * Insert multiple order items
     *
     * @param items Order items to insert
     */
    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    /**
     * Update order status
     *
     * @param orderId Order ID
     * @param status New status
     */

    @Query("UPDATE orders SET status = :status WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: String, status: String)

    /**
     * Insert order with items in a single transaction
     *
     * Ensures atomic operation - either both succeed or both fail
     *
     * @param order Order to insert
     * @param items Order items to insert
     */


    @Transaction
    suspend fun insertOrderWithItems(order: OrderEntity, items: List<OrderItemEntity>) {
        insertOrder(order)
        insertOrderItems(items)
    }
}