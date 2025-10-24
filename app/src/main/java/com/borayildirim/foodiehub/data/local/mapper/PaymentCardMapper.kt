package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.PaymentCardEntity
import com.borayildirim.foodiehub.domain.model.CardType
import com.borayildirim.foodiehub.domain.model.PaymentCard

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