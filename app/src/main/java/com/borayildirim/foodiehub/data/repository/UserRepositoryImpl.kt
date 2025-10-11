package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.AuthException
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val preferencesManager: UserPreferencesManager
) : UserRepository {
    override suspend fun isLogin(): Boolean {
        val userId = preferencesManager.getUserId().firstOrNull()
        return userId != null
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val userEntity = userDao.getUserByEmail(email)
                ?: return Result.failure(AuthException.UserNotFound)

            if (userEntity.password != password) {
                return Result.failure(AuthException.WrongPassword)
            }

            // Save userId to DataStore
            preferencesManager.saveUserId(userEntity.userId)

            Result.success(userEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: User): Result<Unit> {
        return try {
            // Check if email already exists
            val existingUser = userDao.getUserByEmail(user.email)
            if (existingUser != null) {
                return Result.failure(AuthException.EmailAlreadyExists)
            }

            // Insert new user
            userDao.insertUser(user.toEntity())

            // Save userId to DataStore (auto-login)
            preferencesManager.saveUserId(user.userId)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}