package com.borayildirim.foodiehub.data.repository

import androidx.room.Room
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.ProfileRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(): ProfileRepository {

    // Temporary static user - it will change with Room
    private val currentUser = User(
        userId = "temp_user",
        name =  "Sophia Patel",
        email =  "sophiapatel@gmail.com",
        profilePicture = null,
        isLogin = true,
        deliveryAdress = "123 Main St Apartment 4A, New York, NY",
        phoneNumber = "+1 555-0123"
    )

    override suspend fun getCurrentUser(): User? = currentUser

    override suspend fun updateUserProfile(user: User): Result<Unit> {
        // TODO: Room Database Implementation
        // Temporary: Just return success for now
        return Result.success(Unit)
    }

    override suspend fun updateDeliveryAddress(address: String): Result<Unit> {
        // TODO: Room Database Implementation
        // Temporary: Just return success for now
        return Result.success(Unit)
    }

    override suspend fun logout(): Result<Unit> {
        // TODO: Authentication logic
        // Temporary: Just return success for now
        return Result.success(Unit)
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String) {
        // Mock success for now
        delay(500) // Simulate network delay
        // Mock validation - for test
        if (currentPassword != "123456") {
            throw Exception("Mevcut şifre yanlış")
        }
        // Success --> Do nothing
    }
}