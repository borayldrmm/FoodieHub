package com.borayildirim.foodiehub

import android.app.Application
import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.entity.UserEntity
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FoodieHubApplication : Application() {

    @Inject
    lateinit var userDao: UserDao

    override fun onCreate() {
        super.onCreate()

        // Add test user (only first opening)
        CoroutineScope(Dispatchers.IO).launch {
            insertTestUserIfNotExists()

            // TEST: Save userId in DataStore
            val preferencesManager = UserPreferencesManager(this@FoodieHubApplication)
            preferencesManager.saveUserId("temp_user_123")
        }
    }

    private suspend fun insertTestUserIfNotExists() {
        val existingUser = userDao.getUserById("temp_user_123")
        if (existingUser == null) {
            val testUser = UserEntity(
                userId = "temp_user_123",
                fullName = "Sophia Patel",
                email = "sophiapatel@gmail.com",
                phoneNumber = "+1 555-0123",
                password = "123456",
                deliveryAddress = "123 Main St Apartment 4A, New York, NY",
                profilePicture = null
            )
            userDao.insertUser(testUser)
        }
    }
}