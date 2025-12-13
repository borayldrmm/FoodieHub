package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for user account and profile
 *
 * Represents authenticated user with credentials and profile information.
 * Used across app for authentication state and profile display.
 *
 * Security consideration:
 * - Password included for mock authentication
 * - Production apps should exclude password from domain model
 *   and handle separately in authentication layer
 *
 * @property userId Unique identifier (UUID)
 * @property fullName User's display name
 * @property email Unique, used for authentication
 * @property profilePicture Optional file path to profile image
 * @property deliveryAddress Optional default delivery location
 * @property phoneNumber Contact number
 * @property password Plain text (mock only)
 */
data class User(
    val userId: String,
    val fullName: String,
    val email: String,
    val profilePicture: String? = null,
    val deliveryAddress: String? = null,
    val phoneNumber: String,
    val password: String
)