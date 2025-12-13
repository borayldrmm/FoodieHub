package com.borayildirim.foodiehub.domain.usecase.address

import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.repository.AddressRepository
import javax.inject.Inject

/**
 * Use case for updating existing address
 *
 * Delegates to repository which handles automatic default address
 * management. If address is being set as default, repository ensures
 * only one default exists by clearing other defaults first.
 *
 * @see AddressRepository.updateAddress
 */
class UpdateAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    /**
     * Updates existing address in database
     *
     * @param address Address with updated fields
     */
    suspend operator fun invoke(address: Address) {
        repository.updateAddress(address)
    }
}