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

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val preferencesManager: UserPreferencesManager
): ProfileRepository {

    override suspend fun getCurrentUser(): User? {
        val userId = preferencesManager.getUserId().firstOrNull() ?: return null
        val userEntity = userDao.getUserById(userId)
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

    override suspend fun updateDeliveryAddress(
        address: String,
    ): Result<Unit> {
        return try {
            val user = getCurrentUser() ?: return Result.failure(Exception("Kullanıcı bulunamadı!"))
            val updateUser = user.copy(deliveryAddress = address)
            userDao.updateUser(updateUser.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ) {
        val user = getCurrentUser() ?: throw Exception("Kullanıcı bulunamadı!")

        if (user.password != currentPassword) {
            throw Exception("Mevcut şifre yanlış!")
        }

        val updateUser = user.copy(password = newPassword)
        userDao.updateUser(updateUser.toEntity())
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