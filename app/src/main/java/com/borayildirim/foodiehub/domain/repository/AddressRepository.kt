package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    fun getUserAddresses(userId: String): Flow<List<Address>>
    fun getDefaultAddress(userId: String): Flow<Address?>
    suspend fun insertAddress(address: Address)
    suspend fun updateAddress(address: Address)
    suspend fun deleteAddress(address: Address)
    suspend fun setDefaultAddress(userId: String, addressId: String)
}