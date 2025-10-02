package com.borayildirim.foodiehub.domain.model

data class PaymentCard(
    val id: String,
    val cardNumber: String,     // Masked: "****1234"
    val cardHolderName: String,
    val expiryDate: String,     // "MM/YY" format
    val cardType: CardType,
    val isDefault: Boolean = false
)

enum class CardType {
    VISA,
    MASTERCARD,
    AMERICAN_EXPRESS;

    fun getDisplayName(): String {
        return when(this) {
            VISA -> "Visa"
            MASTERCARD -> "Mastercard"
            AMERICAN_EXPRESS -> "American Express"
        }
    }
}