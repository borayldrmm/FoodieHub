package com.borayildirim.foodiehub.domain.model

/**
 * Domain model for payment card with masked number for security
 *
 * Represents user payment method with security-conscious design.
 * Card numbers are always masked (e.g., "****1234") to prevent
 * exposure of sensitive payment data.
 *
 * @property id Unique identifier (UUID)
 * @property cardNumber Masked format "****1234" for display only
 * @property cardHolderName Name as appears on card
 * @property expiryDate Format "MM/YY"
 * @property cardType Card brand for icon display
 * @property isDefault Whether this is user's primary payment method
 */
data class PaymentCard(
    val id: String,
    val cardNumber: String,
    val cardHolderName: String,
    val expiryDate: String,
    val cardType: CardType,
    val isDefault: Boolean = false
)

/**
 * Supported payment card types
 *
 * Determines card brand icon and validation rules.
 */
enum class CardType {
    VISA,
    MASTERCARD,
    AMERICAN_EXPRESS;

    /**
     * Returns user-friendly display name for card type
     */
    fun getDisplayName(): String {
        return when(this) {
            VISA -> "Visa"
            MASTERCARD -> "Mastercard"
            AMERICAN_EXPRESS -> "American Express"
        }
    }
}