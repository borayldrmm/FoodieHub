package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.User

/**
 * Repository interface for user profile management operations
 *
 * Provides profile updates, password changes, and logout functionality
 * with session validation. Uses Result type for explicit error handling
 * except where exceptions are more appropriate (password change).
 *
 * Business rules:
 * - All operations require active session (userId in DataStore)
 * - Password change requires current password verification
 * - Logout clears session but preserves user data
 *
 * Architecture:
 * - Session validation via DataStore
 * - Profile storage via Room
 * - Result type for recoverable errors
 * - Exceptions for authentication failures
 */
interface ProfileRepository {
    /**
     * Retrieves currently authenticated user profile
     *
     * Validates session via DataStore userId, then fetches full profile
     * from database. Returns null if no active session.
     *
     * @return Current user or null if not authenticated
     */
    suspend fun getCurrentUser(): User?

    /**
     * Updates user profile information
     *
     * @param user Complete user object with updated fields
     * @return Result.success(Unit) or Result.failure(Exception)
     */
    suspend fun updateUserProfile(user: User): Result<Unit>

    /**
     * Updates user's default delivery address
     *
     * Convenience method for quick address updates without full profile.
     *
     * @param address New delivery address string
     * @return Result.success(Unit) or Result.failure if user not found
     */
    suspend fun updateDeliveryAddress(address: String): Result<Unit>

    /**
     * Ends user session by clearing DataStore
     *
     * Clears session state but preserves user data in database.
     * User can log back in with same credentials.
     *
     * @return Result.success(Unit) or Result.failure(Exception)
     */
    suspend fun logout(): Result<Unit>

    /**
     * Changes user password with verification
     *
     * Validates current password before updating to new one.
     * Uses exceptions for authentication failures rather than Result
     * for clearer error semantics.
     *
     * @param currentPassword Current password for verification
     * @param newPassword New password to set
     * @throws Exception if user not found or current password incorrect
     */
    suspend fun changePassword(currentPassword: String, newPassword: String)
}