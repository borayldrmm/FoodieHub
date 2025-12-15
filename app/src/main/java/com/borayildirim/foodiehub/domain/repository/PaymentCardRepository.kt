package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.PaymentCard
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for payment card operations
 *
 * Provides secure payment card management with masked card numbers
 * and automatic default card handling. Implementations ensure only
 * one default card exists per user.
 *
 * Security considerations:
 * - Only masked card numbers are stored and retrieved
 * - CVV codes are never persisted
 * - Full card numbers (PANs) are never stored
 *
 * Business rules:
 * - Each user can have multiple cards
 * - Only one card can be marked as default per user
 * - Setting new default automatically unmarks previous default
 */
interface PaymentCardRepository {
    /**
     * Retrieves all payment cards for user with reactive updates
     *
     * @param userId User identifier
     * @return Flow emitting card list on any card changes
     */
    fun getUserCards(userId: String): Flow<List<PaymentCard>>

    /**
     * Adds new payment card to user account
     *
     * Card number must already be masked before calling this method.
     *
     * @param userId User identifier
     * @param card Payment card with masked number
     */
    suspend fun addCard(userId: String, card: PaymentCard)

    /**
     * Removes payment card from user account
     *
     * @param cardId Card identifier to delete
     */
    suspend fun deleteCard(cardId: String)

    /**
     * Sets card as default payment method
     *
     * Atomically clears other defaults and marks specified card,
     * ensuring only one default exists per user.
     *
     * @param userId User identifier
     * @param cardId Card identifier to set as default
     */
    suspend fun setDefaultCard(userId: String, cardId: String)
}