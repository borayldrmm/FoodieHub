package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for user data storage
 *
 * @property userId Primary key (UUID)
 * @property email Unique, indexed for fast authentication lookup
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