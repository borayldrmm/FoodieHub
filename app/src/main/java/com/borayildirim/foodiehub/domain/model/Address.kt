package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for delivery address with default management
 *
 * Represents user delivery location with complete contact information.
 * Only one address can be marked as default per user, enforced by
 * repository logic.
 *
 * @property id Unique identifier (UUID)
 * @property userId Owner's user ID
 * @property title User-defined label for quick identification
 * @property addressType Address category for icon/grouping
 * @property fullAddress Complete street address
 * @property city City name
 * @property district District/neighborhood
 * @property zipCode Optional postal code
 * @property phoneNumber Contact number for delivery driver
 * @property isDefault Whether this is user's primary delivery address
 * @property createdAt Creation timestamp in milliseconds
 * @property updatedAt Last modification timestamp in milliseconds
 */
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

/**
 * Address type enum for categorization
 *
 * Used for icon display and address grouping in UI.
 */
enum class AddressType {
    /** Residential address */
    HOME,

    /** Workplace address */
    WORK,

    /** Other locations (friend's house, hotel, etc.) */
    OTHER
}