package com.borayildirim.foodiehub.domain.usecase

import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.repository.PaymentCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserCardsUseCase @Inject constructor(
    private val repository: PaymentCardRepository
) {
    operator fun invoke(userId: String): Flow<List<PaymentCard>> {
        return repository.getUserCards(userId)
    }
}