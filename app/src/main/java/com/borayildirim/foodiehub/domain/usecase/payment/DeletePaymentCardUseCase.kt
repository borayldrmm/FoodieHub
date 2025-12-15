package com.borayildirim.foodiehub.domain.usecase.payment

import com.borayildirim.foodiehub.domain.repository.PaymentCardRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for deleting payment card from user account
 */
@Singleton
class DeletePaymentCardUseCase @Inject constructor(
    private val repository: PaymentCardRepository
) {
    suspend operator fun invoke(cardId: String) {
        repository.deleteCard(cardId)
    }
}