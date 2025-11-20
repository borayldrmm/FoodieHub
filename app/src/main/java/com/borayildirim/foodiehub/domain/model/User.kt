package com.borayildirim.foodiehub.domain.model

/**
 * Domain model representing a user
 *
 * @property userId Unique identifier (UUID)
 * @property email Unique, used for authentication
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