package com.borayildirim.foodiehub.domain.usecase.address

import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving all user addresses
 *
 * Returns reactive Flow for real-time address list updates.
 */
class GetUserAddressesUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(userId: String): Flow<List<Address>> {
        return repository.getUserAddresses(userId)
    }
}