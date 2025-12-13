package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.borayildirim.foodiehub.data.local.entity.AddressEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for address database operations with default management
 *
 * Provides atomic transaction for setting default addresses to
 * ensure data consistency and prevent race conditions.
 */
@Dao
interface AddressDao {

    @Query("SELECT * FROM addresses WHERE userId = :userId")
    fun getUserAddresses(userId: String): Flow<List<AddressEntity>>

    @Query("SELECT * FROM addresses WHERE userId = :userId AND isDefault = 1 LIMIT 1")
    fun getDefaultAddress(userId: String): Flow<AddressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressEntity)

    @Update
    suspend fun updateAddress(address: AddressEntity)

    @Delete
    suspend fun deleteAddress(address: AddressEntity)

    /**
     * Clears default flag from all user addresses
     *
     * Used before setting a new default to ensure only one
     * default address exists per user.
     *
     * @param userId User ID
     */
    @Query("UPDATE addresses SET isDefault = 0 WHERE userId = :userId")
    suspend fun clearAllDefaults(userId: String)

    @Query("UPDATE addresses SET isDefault = 1 WHERE id = :addressId")
    suspend fun markAsDefault(addressId: String)

    /**
     * Atomically sets address as default with transaction
     *
     * Clears all defaults then marks specified address as default
     * in a single transaction to prevent race conditions.
     *
     * @param userId User ID
     * @param addressId Address ID to set as default
     */
    @Transaction
    suspend fun setDefaultAddress(userId: String, addressId: String) {
        clearAllDefaults(userId)
        markAsDefault(addressId)
    }
}