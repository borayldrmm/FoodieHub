package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "addresses",
    indices = [Index(value = ["userId"])]
)
data class AddressEntity (
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,              // "Home", "Work", "Other Address"
    val addressType: String,        // HOME, WORK, OTHER
    val fullAddress: String,        // Address
    val city: String,
    val district: String,
    val zipCode: String?,
    val phoneNumber: String,
    val isDefault: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)