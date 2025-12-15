package com.borayildirim.foodiehub.domain.usecase.address

import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving user's default address
 *
 * Returns reactive Flow for automatic UI updates when default changes.
 */
class GetDefaultAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    operator fun invoke(userId: String): Flow<Address?> {
        return repository.getDefaultAddress(userId)
    }
}