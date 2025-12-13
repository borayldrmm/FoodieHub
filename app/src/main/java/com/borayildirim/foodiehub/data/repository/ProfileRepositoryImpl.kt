package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for user profile operations
 *
 * Manages profile updates and password changes with session validation
 * via DataStore. Provides type-safe error handling with Result type.
 *
 * Architecture:
 * - DataStore: Session management (userId)
 * - Room: User profile storage
 * - Result type: Explicit error handling
 */
@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val preferencesManager: UserPreferencesManager
): ProfileRepository {

    override suspend fun getCurrentUser(): User? {
        val userId = preferencesManager.getUserId().firstOrNull() ?: return null
        val userEntity = userDao.getUserById(userId).firstOrNull()
        return userEntity?.toDomain()
    }

    override suspend fun updateUserProfile(user: User): Result<Unit> {
        return try {
            userDao.updateUser(user.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDeliveryAddress(address: String): Result<Unit> {
        return try {
            val user = getCurrentUser()
                ?: return Result.failure(Exception("Kullanıcı bulunamadı!"))
            val updatedUser = user.copy(deliveryAddress = address)
            userDao.updateUser(updatedUser.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Changes user password with current password verification
     *
     * @throws Exception if user not found or current password incorrect
     */
    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ) {
        val user = getCurrentUser() ?: throw Exception("Kullanıcı bulunamadı!")

        if (user.password != currentPassword) {
            throw Exception("Mevcut şifre yanlış!")
        }

        val updatedUser = user.copy(password = newPassword)
        userDao.updateUser(updatedUser.toEntity())
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            preferencesManager.clearUserId()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}