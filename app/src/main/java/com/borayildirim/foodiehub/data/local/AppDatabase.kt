package com.borayildirim.foodiehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borayildirim.foodiehub.data.local.dao.CartDao
import com.borayildirim.foodiehub.data.local.dao.FoodDao
import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.entity.CartItemEntity
import com.borayildirim.foodiehub.data.local.entity.FoodEntity
import com.borayildirim.foodiehub.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        FoodEntity::class,
        CartItemEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    abstract fun cartDao(): CartDao
}