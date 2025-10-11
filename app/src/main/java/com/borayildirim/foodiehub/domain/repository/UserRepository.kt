package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.User

interface UserRepository {
    suspend fun isLogin(): Boolean
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(user: User): Result<Unit>
}