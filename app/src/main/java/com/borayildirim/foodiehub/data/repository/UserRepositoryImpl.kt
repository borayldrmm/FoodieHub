package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun isLogin(): Boolean {
        return false
    }

}