package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckUserLoginStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun checkLoginStatus(): Boolean {
        return userRepository.isLogin()
    }
}