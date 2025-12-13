package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for delivery addresses with default management
 *
 * Stores user delivery addresses with indexed userId for fast queries.
 * Supports multiple addresses per user with single-default enforcement
 * via repository logic.
 *
 * @property id Unique address identifier (UUID)
 * @property userId Owner's user ID (indexed for performance)
 * @property title User-defined label ("Home", "Work", "Office")
 * @property addressType Enum as string (HOME, WORK, OTHER)
 * @property fullAddress Complete street address
 * @property city City name
 * @property district District/neighborhood
 * @property zipCode Optional postal code
 * @property phoneNumber Contact number for delivery
 * @property isDefault Whether this is user's default delivery address
 * @property createdAt Timestamp in milliseconds
 * @property updatedAt Timestamp in milliseconds
 */
@Entity(
    tableName = "addresses",
    indices = [Index(value = ["userId"])]
)
data class AddressEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val addressType: String,
    val fullAddress: String,
    val city: String,
    val district: String,
    val zipCode: String?,
    val phoneNumber: String,
    val isDefault: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)