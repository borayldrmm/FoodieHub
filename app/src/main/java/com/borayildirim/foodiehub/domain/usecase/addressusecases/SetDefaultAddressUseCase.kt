package com.borayildirim.foodiehub.domain.usecase.addressusecases

import com.borayildirim.foodiehub.domain.repository.AddressRepository
import javax.inject.Inject

class SetDefaultAddressUseCase @Inject constructor(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(userId: String, addressId: String) {
        repository.setDefaultAddress(userId, addressId)
    }
}