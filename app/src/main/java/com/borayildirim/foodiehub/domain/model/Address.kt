package com.borayildirim.foodiehub.domain.model

data class Address(
    val id: String,
    val userId: String,
    val title: String,
    val addressType: AddressType,
    val fullAddress: String,
    val city: String,
    val district: String,
    val zipCode: String?,
    val phoneNumber: String,
    val isDefault: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

enum class AddressType {
    HOME,
    WORK,
    OTHER
}