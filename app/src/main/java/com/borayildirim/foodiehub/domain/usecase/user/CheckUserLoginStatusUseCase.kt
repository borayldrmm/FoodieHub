package com.borayildirim.foodiehub.domain.usecase.user

import com.borayildirim.foodiehub.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for checking user authentication status
 */
@Singleton
class CheckUserLoginStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun checkLoginStatus(): Boolean {
        return userRepository.isLogin()
    }
}