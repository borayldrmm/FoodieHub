package com.borayildirim.foodiehub.domain.usecase.payment

import com.borayildirim.foodiehub.domain.repository.PaymentCardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDefaultCardUseCase @Inject constructor(
    private val repository: PaymentCardRepository
) {
    suspend operator fun invoke(userId: String, cardId: String) {
        repository.setDefaultCard(userId, cardId)
    }
}