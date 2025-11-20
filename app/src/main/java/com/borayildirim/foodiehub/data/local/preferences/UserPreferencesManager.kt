package com.borayildirim.foodiehub.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences>
    by preferencesDataStore(name = "user_preferences")

/**
 * Manager for user session persistence using DataStore
 *
 * Stores minimal user session data (userId) for authentication state management.
 * Uses DataStore Preferences for type-safe, asynchronous key-value storage.
 *
 * Storage Strategy:
 * - DataStore: Lightweight session data (userId only)
 * - Room: Full user profile (managed by UserRepository)
 */
@Singleton
class UserPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val USER_ID_KEY = stringPreferencesKey("user_id")

    /**
     * Persists user ID for session management
     *
     * Called after successful login or registration to maintain user session.
     */
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    /**
     * Retrieves user ID as Flow for reactive session monitoring
     *
     * Returns null if user is not logged in.
     */
    fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    /**
     * Clears user session on logout
     *
     * Removes userId from DataStore, effectively logging out the user.
     */
    suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }
}