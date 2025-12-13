package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.AuthException
import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for user authentication and profile management
 *
 * Combines Room database for persistent user data with DataStore for
 * lightweight session management. Uses Result type for explicit error
 * handling in authentication flows.
 *
 * Architecture:
 * - Room: User profiles and credentials
 * - DataStore: Session state (userId only)
 * - Result type: Type-safe error handling
 *
 * Security note: Plain text passwords for mock app only.
 */
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

            preferencesManager.saveUserId(userEntity.userId)
            Result.success(userEntity.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: User): Result<Unit> {
        return try {
            val existingUser = userDao.getUserByEmail(user.email)
            if (existingUser != null) {
                return Result.failure(AuthException.EmailAlreadyExists)
            }

            userDao.insertUser(user.toEntity())
            preferencesManager.saveUserId(user.userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserById(userId: String): Flow<User?> {
        return userDao.getUserById(userId).map { it?.toDomain() }
    }
}