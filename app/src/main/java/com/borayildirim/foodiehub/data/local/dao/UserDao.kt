package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.borayildirim.foodiehub.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for user database operations with authentication support
 *
 * Provides both suspend functions for one-time queries (authentication)
 * and Flow queries for reactive profile updates.
 */
@Dao
interface UserDao {
    /**
     * Inserts or replaces user
     *
     * Uses REPLACE strategy for upsert behavior during registration.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    /**
     * Retrieves user by email for authentication
     *
     * Uses indexed email column for fast lookup performance.
     *
     * @param email User's email address
     * @return User entity or null if not found
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    /**
     * Retrieves user by ID with reactive updates
     *
     * Returns Flow for real-time profile synchronization across screens.
     *
     * @param userId User identifier
     * @return Flow emitting user or null on profile changes
     */
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)
}