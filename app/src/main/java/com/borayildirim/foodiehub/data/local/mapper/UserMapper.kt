package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.UserEntity
import com.borayildirim.foodiehub.domain.model.User


// Entity --> Domain
fun UserEntity.toDomain(): User {
    return User(
        userId = this.userId,
        fullName = this.fullName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        password = this.password,
        deliveryAddress = this.deliveryAddress,
        profilePicture = this.profilePicture
    )
}

// Domain --> Entity
fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        fullName = this.fullName,
        email = this.email,
        phoneNumber = this.phoneNumber,
        password = this.password,
        deliveryAddress = this.deliveryAddress,
        profilePicture = this.profilePicture
    )
}