package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.borayildirim.foodiehub.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>)

    @Query("SELECT * FROM foods")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    fun getFoodById(foodId: Int): Flow<FoodEntity?>

    @Query("SELECT * FROM foods WHERE categoryId = :categoryId")
    fun getFoodsByCategory(categoryId: Int): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%'")
    fun searchFoods(query: String): Flow<List<FoodEntity>>

    @Query("DELETE FROM foods")
    suspend fun deleteAll()
}