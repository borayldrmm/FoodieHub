package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.PaymentCardEntity
import com.borayildirim.foodiehub.domain.model.CardType
import com.borayildirim.foodiehub.domain.model.PaymentCard

/**
 * Converts PaymentCardEntity to domain PaymentCard
 *
 * Parses string cardType to enum for type-safe domain representation.
 */
fun PaymentCardEntity.toDomain(): PaymentCard {
    return PaymentCard(
        id = this.cardId,
        cardNumber = this.cardNumber,
        cardHolderName = this.cardHolderName,
        expiryDate = this.expiryDate,
        cardType = CardType.valueOf(this.cardType),
        isDefault = this.isDefault
    )
}

/**
 * Converts domain PaymentCard to entity for database storage
 *
 * @param userId Required for entity storage (not part of domain model)
 */
fun PaymentCard.toEntity(userId: String): PaymentCardEntity {
    return PaymentCardEntity(
        cardId = this.id,
        userId = userId,
        cardNumber = this.cardNumber,
        cardHolderName = this.cardHolderName,
        expiryDate = this.expiryDate,
        cardType = this.cardType.name,
        isDefault = this.isDefault
    )
}