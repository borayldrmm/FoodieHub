package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.User

interface ProfileRepository {
    suspend fun getCurrentUser(): User?
    suspend fun updateUserProfile(user: User): Result<Unit>
    suspend fun updateDeliveryAddress(address: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun changePassword(currentPassword: String, newPassword: String)
}