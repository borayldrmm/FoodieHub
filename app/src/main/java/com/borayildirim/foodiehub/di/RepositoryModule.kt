package com.borayildirim.foodiehub.di

import com.borayildirim.foodiehub.data.repository.CartRepositoryImpl
import com.borayildirim.foodiehub.data.repository.FoodRepositoryImpl
import com.borayildirim.foodiehub.data.repository.ProfileRepositoryImpl
import com.borayildirim.foodiehub.data.repository.UserRepositoryImpl
import com.borayildirim.foodiehub.domain.repository.CartRepository
import com.borayildirim.foodiehub.domain.repository.FoodRepository
import com.borayildirim.foodiehub.domain.repository.ProfileRepository
import com.borayildirim.foodiehub.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindUserRepository(implementation: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindFoodRepository(implementation: FoodRepositoryImpl): FoodRepository

    @Binds
    abstract fun bindCardRepository(implementation: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}