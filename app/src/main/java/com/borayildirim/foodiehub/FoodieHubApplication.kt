package com.borayildirim.foodiehub

import android.app.Application
import com.borayildirim.foodiehub.data.local.dao.FoodDao
import com.borayildirim.foodiehub.data.local.dao.UserDao
import com.borayildirim.foodiehub.data.local.entity.UserEntity
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.MockFoodData
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FoodieHubApplication : Application() {

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var foodDao: FoodDao

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            // Insert test user
            insertTestUserIfNotExists()

            // Instert mock food data
            insertMockFoodDataIfNotExists()
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

    private suspend fun insertMockFoodDataIfNotExists() {
        // Get first emission from Flow (current state)
        val existingFoods = foodDao.getAllFoods().first()

        // Only insert if database is empty
        if (existingFoods.isEmpty()) {
            val mockFoods = MockFoodData.getAllFoods()
            val foodEntities = mockFoods.map { it.toEntity() }
            foodDao.insertAll(foodEntities)
        }
    }
}