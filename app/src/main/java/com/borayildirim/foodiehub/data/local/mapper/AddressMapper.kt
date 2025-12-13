package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.AddressEntity
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.model.AddressType

/**
 * Converts AddressEntity to domain Address
 *
 * Parses string addressType to enum for type-safe domain representation.
 */
fun AddressEntity.toDomain(): Address {
    return Address(
        id = id,
        userId = userId,
        title = title,
        addressType = AddressType.valueOf(addressType),
        fullAddress = fullAddress,
        city = city,
        district = district,
        zipCode = zipCode,
        phoneNumber = phoneNumber,
        isDefault = isDefault,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts domain Address to entity for database storage
 *
 * Serializes enum to string for Room compatibility.
 */
fun Address.toEntity(): AddressEntity {
    return AddressEntity(
        id = id,
        userId = userId,
        title = title,
        addressType = addressType.name,
        fullAddress = fullAddress,
        city = city,
        district = district,
        zipCode = zipCode,
        phoneNumber = phoneNumber,
        isDefault = isDefault,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}