package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.PaymentCardDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.domain.model.PaymentCard
import com.borayildirim.foodiehub.domain.repository.PaymentCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for payment card operations
 *
 * Manages payment cards with automatic default card handling,
 * ensuring only one default card exists per user at any time.
 *
 * Security note: Only stores masked card numbers, never full PANs or CVVs.
 */
@Singleton
class PaymentCardRepositoryImpl @Inject constructor(
    private val paymentCardDao: PaymentCardDao
) : PaymentCardRepository {

    override fun getUserCards(userId: String): Flow<List<PaymentCard>> {
        return paymentCardDao.getUserCards(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addCard(userId: String, card: PaymentCard) {
        paymentCardDao.insertCard(card.toEntity(userId))
    }

    override suspend fun deleteCard(cardId: String) {
        paymentCardDao.deleteCard(cardId)
    }

    /**
     * Sets card as default with atomic default clearing
     *
     * Clears all other defaults before marking specified card
     * to maintain single-default constraint.
     */
    override suspend fun setDefaultCard(userId: String, cardId: String) {
        paymentCardDao.clearDefaultCards(userId)
        paymentCardDao.setDefaultCard(cardId)
    }
}