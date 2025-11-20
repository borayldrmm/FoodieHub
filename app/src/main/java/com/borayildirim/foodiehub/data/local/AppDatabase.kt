package com.borayildirim.foodiehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borayildirim.foodiehub.data.local.dao.AddressDao
import com.borayildirim.foodiehub.data.local.dao.CartDao
import com.borayildirim.foodiehub.data.local.dao.FoodDao
import com.borayildirim.foodiehub.data.local.dao.OrderDao
import com.borayildirim.foodiehub.data.local.dao.PaymentCardDao
import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.entity.AddressEntity
import com.borayildirim.foodiehub.data.local.entity.CartItemEntity
import com.borayildirim.foodiehub.data.local.entity.FoodEntity
import com.borayildirim.foodiehub.data.local.entity.OrderEntity
import com.borayildirim.foodiehub.data.local.entity.OrderItemEntity
import com.borayildirim.foodiehub.data.local.entity.PaymentCardEntity
import com.borayildirim.foodiehub.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        FoodEntity::class,
        CartItemEntity::class,
        PaymentCardEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        AddressEntity::class
    ],
    version = 6,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    abstract fun cartDao(): CartDao
    abstract fun paymentCardDao(): PaymentCardDao
    abstract fun orderDao(): OrderDao
    abstract fun addressDao(): AddressDao
}