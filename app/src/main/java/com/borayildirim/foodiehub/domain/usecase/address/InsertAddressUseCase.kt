package com.borayildirim.foodiehub.domain.usecase.address

import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.repository.AddressRepository
import javax.inject.Inject

/**
 * Use case for inserting new address
 *
 * Delegates to repository which handles automatic default address
 * management. If the new address is marked as default, repository
 * ensures only one default exists by clearing other defaults first.
 *
 * @see AddressRepository.insertAddress
 */
class InsertAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    /**
     * Inserts new address into database
     *
     * @param address Address to insert with all required fields
     */
    suspend operator fun invoke(address: Address) {
        repository.insertAddress(address)
    }
}