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

@Singleton
class AddressRepositoryImpl @Inject constructor(
    private val addressDao: AddressDao
) : AddressRepository  {

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

    override suspend fun insertAddress(address: Address) {
        addressDao.insertAddress(address.toEntity())
    }

    override suspend fun updateAddress(address: Address) {
        addressDao.updateAddress(address.toEntity())
    }

    override suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address.toEntity())
    }

    override suspend fun setDefaultAddress(userId: String, addressId: String) {
        addressDao.setDefaultAddress(userId, addressId)
    }

}