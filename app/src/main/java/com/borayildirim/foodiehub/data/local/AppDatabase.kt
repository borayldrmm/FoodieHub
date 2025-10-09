package com.borayildirim.foodiehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao
}