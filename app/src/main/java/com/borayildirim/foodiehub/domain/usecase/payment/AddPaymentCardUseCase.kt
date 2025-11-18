package com.borayildirim.foodiehub.domain.usecase.payment

import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.repository.PaymentCardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddPaymentCardUseCase @Inject constructor(
    private val repository: PaymentCardRepository
) {
    suspend operator fun invoke(userId: String, card: PaymentCard) {
        repository.addCard(userId, card)
    }
}