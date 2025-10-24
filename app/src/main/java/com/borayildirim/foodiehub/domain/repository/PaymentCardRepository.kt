package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.PaymentCard
import kotlinx.coroutines.flow.Flow

interface PaymentCardRepository {
    fun getUserCards(userId: String): Flow<List<PaymentCard>>
    suspend fun addCard(userId: String, card: PaymentCard)
    suspend fun deleteCard(cardId: String)
    suspend fun setDefaultCard(userId: String, cardId: String)
}