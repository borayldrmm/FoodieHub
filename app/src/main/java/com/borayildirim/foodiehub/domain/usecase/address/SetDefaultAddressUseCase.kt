package com.borayildirim.foodiehub.domain.usecase.address

import com.borayildirim.foodiehub.domain.repository.AddressRepository
import javax.inject.Inject

/**
 * Use case for setting address as default
 *
 * Atomically clears other defaults and marks specified address,
 * ensuring only one default exists per user.
 */
class SetDefaultAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(userId: String, addressId: String) {
        repository.setDefaultAddress(userId, addressId)
    }
}