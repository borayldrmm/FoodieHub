package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for user account data with authentication
 *
 * Stores user credentials and profile information with indexed email
 * for fast authentication queries. Email uniqueness is enforced at
 * database level via unique index constraint.
 *
 * Security consideration:
 * - Password stored as plain text for mock app
 * - Production apps should use hashed passwords (bcrypt/Argon2)
 *
 * @property userId Primary key (UUID)
 * @property fullName User's display name
 * @property email Unique, indexed for authentication lookup
 * @property phoneNumber Contact number
 * @property password Plain text (mock only - hash in production)
 * @property deliveryAddress Optional default delivery location
 * @property profilePicture Optional file path to profile image
 */
@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey val userId: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val deliveryAddress: String?,
    val profilePicture: String?
)