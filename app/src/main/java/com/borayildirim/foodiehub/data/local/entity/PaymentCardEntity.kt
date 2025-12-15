package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for payment card storage with masked card numbers
 *
 * Stores payment cards with security-conscious design using masked numbers.
 * Never stores CVV or full card numbers for PCI compliance best practices.
 *
 * @property cardId Unique identifier (UUID)
 * @property userId Owner's user ID
 * @property cardNumber Masked format "5105 **** **** 0505" for display
 * @property cardHolderName Name as appears on card
 * @property expiryDate Format "MM/YY"
 * @property cardType VISA, MASTERCARD, or AMERICAN_EXPRESS
 * @property isDefault Whether this is user's default payment method
 */
@Entity(tableName = "payment_cards")
data class PaymentCardEntity(
    @PrimaryKey
    val cardId: String,
    val userId: String,
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cardType: String,
    val isDefault: Boolean = false
)