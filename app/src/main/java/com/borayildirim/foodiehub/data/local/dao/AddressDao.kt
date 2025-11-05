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

    @Query("UPDATE addresses SET isDefault = 0 WHERE userId = :userId")
    suspend fun clearAllDefaults(userId: String)

    @Query("UPDATE addresses SET isDefault = 1 WHERE id = :addressId")
    suspend fun markAsDefault(addressId: String)

    @Transaction
    suspend fun setDefaultAddress(userId: String, addressId: String) {
        clearAllDefaults(userId)
        markAsDefault(addressId)
    }

}