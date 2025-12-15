package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borayildirim.foodiehub.data.local.entity.PaymentCardEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for payment card database operations
 *
 * Manages user payment cards with reactive Flow queries and automatic
 * default card management to ensure only one default exists per user.
 */
@Dao
interface PaymentCardDao {

    /**
     * Retrieves all payment cards for user as reactive Flow
     *
     * Emits new list whenever cards change for automatic UI updates.
     */
    @Query("SELECT * FROM payment_cards WHERE userId = :userId")
    fun getUserCards(userId: String): Flow<List<PaymentCardEntity>>

    /**
     * Inserts or replaces payment card
     *
     * Uses REPLACE strategy for upsert behavior.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: PaymentCardEntity)

    @Query("DELETE FROM payment_cards WHERE cardId = :cardId")
    suspend fun deleteCard(cardId: String)

    /**
     * Clears default flag from all user cards
     *
     * Used before setting new default to ensure single-default constraint.
     */
    @Query("UPDATE payment_cards SET isDefault = 0 WHERE userId = :userId")
    suspend fun clearDefaultCards(userId: String)

    @Query("UPDATE payment_cards SET isDefault = 1 WHERE cardId = :cardId")
    suspend fun setDefaultCard(cardId: String)
}