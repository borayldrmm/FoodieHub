package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borayildirim.foodiehub.data.local.entity.PaymentCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentCardDao {

    @Query("SELECT * FROM payment_cards WHERE userId = :userId")
    fun getUserCards(userId: String): Flow<List<PaymentCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: PaymentCardEntity)

    @Query("DELETE FROM payment_cards WHERE cardId = :cardId")
    suspend fun deleteCard(cardId: String)

    @Query("UPDATE payment_cards SET isDefault = 0 WHERE userId = :userId")
    suspend fun clearDefaultCards(userId: String)

    @Query("UPDATE payment_cards SET isDefault = 1 WHERE cardId = :cardId")
    suspend fun setDefaultCard(cardId: String)
}