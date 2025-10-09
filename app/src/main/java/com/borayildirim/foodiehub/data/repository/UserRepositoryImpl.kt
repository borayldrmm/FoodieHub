package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
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
}