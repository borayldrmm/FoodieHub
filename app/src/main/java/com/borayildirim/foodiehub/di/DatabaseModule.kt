package com.borayildirim.foodiehub.di

import android.content.Context
import androidx.room.Room
import com.borayildirim.foodiehub.data.local.AppDatabase
import com.borayildirim.foodiehub.data.local.dao.CartDao
import com.borayildirim.foodiehub.data.local.dao.FoodDao
import com.borayildirim.foodiehub.data.local.dao.PaymentCardDao
import com.borayildirim.foodiehub.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "foodiehub_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideFoodDao(database: AppDatabase): FoodDao {
        return database.foodDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun providePaymentCardDao(database: AppDatabase): PaymentCardDao {
        return database.paymentCardDao()
    }
}