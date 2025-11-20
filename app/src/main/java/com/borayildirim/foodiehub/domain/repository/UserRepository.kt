package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository for user authentication and profile management
 */
interface UserRepository {
    suspend fun isLogin(): Boolean

    /**
     * @throws AuthException.UserNotFound
     * @throws AuthException.WrongPassword
     */
    suspend fun login(email: String, password: String): Result<User>

    /**
     * @throws AuthException.EmailAlreadyExists
     */
    suspend fun register(user: User): Result<Unit>

    /**
     * Returns Flow for real-time profile updates
     */
    fun getUserById(userId: String): Flow<User?>
}