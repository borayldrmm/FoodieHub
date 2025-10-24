package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_cards")
data class PaymentCardEntity(
    @PrimaryKey
    val cardId: String,
    val userId: String,
    val cardNumber: String,     // Masked: "5105 **** **** 0505"
    val cardHolderName: String,
    val expiryDate: String,     // "MM/YY"
    val cardType: String,       // "VISA", "MASTERCARD", "AMERICAN_EXPRESS"
    val isDefault: Boolean = false
)