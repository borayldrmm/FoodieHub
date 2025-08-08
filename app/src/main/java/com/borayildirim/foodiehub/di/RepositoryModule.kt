package com.borayildirim.foodiehub.di

import com.borayildirim.foodiehub.data.repository.UserRepositoryImpl
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
}