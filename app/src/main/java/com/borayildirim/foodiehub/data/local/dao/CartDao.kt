package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.borayildirim.foodiehub.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for shopping cart database operations
 *
 * Manages cart items with reactive Flow queries for real-time UI updates.
 * Supports CRUD operations and cart clearing for checkout completion.
 */
@Dao
interface CartDao {

    /**
     * Inserts or replaces cart item
     *
     * Uses REPLACE strategy to update quantity if item already exists.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    /**
     * Retrieves all cart items as reactive Flow
     *
     * Emits new list whenever cart changes for automatic UI updates.
     */
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE itemId = :itemId")
    fun getCartItemById(itemId: String): Flow<CartItemEntity?>

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)

    /**
     * Clears entire cart
     *
     * Typically called after successful order placement.
     */
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}