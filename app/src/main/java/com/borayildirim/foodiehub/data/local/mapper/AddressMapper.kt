package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.AddressEntity
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.model.AddressType

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