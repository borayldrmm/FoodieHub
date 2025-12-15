package com.borayildirim.foodiehub.data.local.mapper

import com.borayildirim.foodiehub.data.local.entity.UserEntity
import com.borayildirim.foodiehub.domain.model.User

/**
 * Converts UserEntity to domain User
 *
 * Simple 1:1 mapping as entity and domain models have same structure.
 */
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

/**
 * Converts domain User to entity for database storage
 */
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