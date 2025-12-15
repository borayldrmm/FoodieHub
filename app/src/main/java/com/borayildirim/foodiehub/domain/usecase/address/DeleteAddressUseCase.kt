package com.borayildirim.foodiehub.domain.usecase.address

import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.repository.AddressRepository
import javax.inject.Inject

/**
 * Use case for deleting user address
 */
class DeleteAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(address: Address) {
        repository.deleteAddress(address)
    }
}