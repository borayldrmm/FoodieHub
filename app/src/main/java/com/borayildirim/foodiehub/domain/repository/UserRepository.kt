package com.borayildirim.foodiehub.domain.repository

interface UserRepository {
    suspend fun isLogin(): Boolean

}