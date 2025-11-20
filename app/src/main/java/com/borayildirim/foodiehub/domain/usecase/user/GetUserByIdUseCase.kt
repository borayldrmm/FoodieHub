package com.borayildirim.foodiehub.domain.usecase.user

import com.borayildirim.foodiehub.domain.model.User
import com.borayildirim.foodiehub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving user profile by ID
 *
 * Returns reactive Flow for real-time profile updates,
 * enabling profile image sync between screens.
 */
class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<User?> {
        return userRepository.getUserById(userId)
    }
}