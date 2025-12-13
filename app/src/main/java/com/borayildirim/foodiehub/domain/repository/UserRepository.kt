package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for user authentication and profile management
 *
 * Provides authentication operations with type-safe error handling via
 * Result type, and reactive profile queries via Flow for real-time updates.
 *
 * Authentication flow:
 * 1. User enters credentials
 * 2. Repository validates against database
 * 3. On success: Saves session to DataStore
 * 4. Returns Result<User> for error handling
 *
 * Session management:
 * - DataStore stores userId for lightweight session state
 * - Room stores complete user profile
 * - isLogin() checks DataStore for quick session validation
 *
 * Error handling:
 * - Uses Result type for explicit failure cases
 * - Custom AuthException for domain-specific errors
 * - Type-safe error handling in presentation layer
 */
interface UserRepository {
    /**
     * Checks if user is currently authenticated
     *
     * Queries DataStore for userId presence as quick session check.
     * Does not verify user still exists in database.
     *
     * @return true if session exists, false otherwise
     */
    suspend fun isLogin(): Boolean

    /**
     * Authenticates user with email and password
     *
     * Validates credentials against database and creates session
     * on success. Returns typed errors for specific failure cases.
     *
     * @param email User's email address
     * @param password Plain text password (mock only)
     * @return Result.success(User) or Result.failure(AuthException)
     * @throws AuthException.UserNotFound if email not in database
     * @throws AuthException.WrongPassword if password doesn't match
     */
    suspend fun login(email: String, password: String): Result<User>

    /**
     * Registers new user account
     *
     * Creates user in database and initiates session. Enforces email
     * uniqueness via database constraint and pre-check.
     *
     * @param user Complete user data including credentials
     * @return Result.success(Unit) or Result.failure(AuthException)
     * @throws AuthException.EmailAlreadyExists if email already registered
     */
    suspend fun register(user: User): Result<Unit>

    /**
     * Retrieves user profile by ID with reactive updates
     *
     * Returns Flow for real-time profile synchronization. Emits new
     * user data whenever profile is updated (e.g., profile picture change).
     *
     * @param userId User identifier
     * @return Flow emitting user or null if not found
     */
    fun getUserById(userId: String): Flow<User?>
}