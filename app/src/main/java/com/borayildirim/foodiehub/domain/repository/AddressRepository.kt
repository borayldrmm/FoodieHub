package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Address
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for address management operations
 *
 * Provides delivery address CRUD with automatic default address handling.
 * Ensures business rule: only one default address exists per user.
 *
 * Business rules:
 * - Each user can have multiple addresses
 * - Only one address can be default per user
 * - Setting new default automatically unmarks previous default
 * - Deleting default address doesn't auto-promote another
 *
 * Architecture:
 * - Reactive Flow queries for real-time address list updates
 * - Repository enforces single-default constraint
 * - Timestamps track address creation and modifications
 */
interface AddressRepository {
    /**
     * Retrieves all addresses for user with reactive updates
     *
     * @param userId User identifier
     * @return Flow emitting address list on any changes
     */
    fun getUserAddresses(userId: String): Flow<List<Address>>

    /**
     * Retrieves user's default address with reactive updates
     *
     * Returns null if user has no default set.
     *
     * @param userId User identifier
     * @return Flow emitting default address or null
     */
    fun getDefaultAddress(userId: String): Flow<Address?>

    /**
     * Inserts new address with automatic default management
     *
     * If address.isDefault is true, clears default flag from all
     * other user addresses before insertion to maintain single-default rule.
     *
     * @param address Address to insert with all required fields
     */
    suspend fun insertAddress(address: Address)

    /**
     * Updates existing address with automatic default management
     *
     * If address.isDefault is true, clears default flag from all
     * other user addresses before update to maintain single-default rule.
     *
     * @param address Address with updated fields
     */
    suspend fun updateAddress(address: Address)

    /**
     * Deletes address from user account
     *
     * Note: Deleting default address does not auto-promote another.
     * User must explicitly set new default if needed.
     *
     * @param address Address to delete
     */
    suspend fun deleteAddress(address: Address)

    /**
     * Sets specific address as default with atomic operation
     *
     * Uses transaction to atomically clear all defaults and mark
     * specified address, ensuring only one default exists per user.
     *
     * @param userId User identifier
     * @param addressId Address identifier to set as default
     */
    suspend fun setDefaultAddress(userId: String, addressId: String)
}