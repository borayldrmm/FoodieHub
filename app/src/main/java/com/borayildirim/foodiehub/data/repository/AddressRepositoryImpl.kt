package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.AddressDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for address data management
 *
 * Handles automatic default address management by ensuring only one
 * default address exists per user. When inserting or updating an
 * address with isDefault=true, automatically clears default flag
 * from all other user addresses.
 *
 * Features:
 * - Automatic single-default enforcement
 * - Reactive Flow-based data updates
 * - Clean separation between entity and domain models
 */
@Singleton
class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao
) : AddressRepository {

    override fun getUserAddresses(userId: String): Flow<List<Address>> {
        return addressDao.getUserAddresses(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getDefaultAddress(userId: String): Flow<Address?> {
        return addressDao.getDefaultAddress(userId).map { entity ->
            entity?.toDomain()
        }
    }

    /**
     * Inserts new address with automatic default management
     *
     * If address.isDefault is true, clears default flag from all
     * other user addresses before insertion to ensure only one
     * default address exists.
     *
     * @param address Address to insert
     */
    override suspend fun insertAddress(address: Address) {
        if (address.isDefault) {
            addressDao.clearAllDefaults(address.userId)
        }
        addressDao.insertAddress(address.toEntity())
    }

    /**
     * Updates existing address with automatic default management
     *
     * If address.isDefault is true, clears default flag from all
     * other user addresses before update to ensure only one
     * default address exists.
     *
     * @param address Address with updated fields
     */
    override suspend fun updateAddress(address: Address) {
        if (address.isDefault) {
            addressDao.clearAllDefaults(address.userId)
        }
        addressDao.updateAddress(address.toEntity())
    }

    override suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address.toEntity())
    }

    /**
     * Sets specific address as default for user
     *
     * Uses transaction to atomically clear all defaults and mark
     * the specified address as default. Ensures data consistency
     * even under concurrent operations.
     *
     * @param userId User ID
     * @param addressId Address ID to set as default
     */
    override suspend fun setDefaultAddress(userId: String, addressId: String) {
        addressDao.setDefaultAddress(userId, addressId)
    }
}