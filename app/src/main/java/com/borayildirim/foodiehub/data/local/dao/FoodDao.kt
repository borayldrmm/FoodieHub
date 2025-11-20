package com.borayildirim.foodiehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.borayildirim.foodiehub.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for food database operations with reactive Flow support
 *
 * Provides both Flow-based reactive queries for UI updates and
 * synchronous queries for one-time operations like favorite toggling.
 */
@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>)

    @Query("SELECT * FROM foods")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    fun getFoodById(foodId: Int): Flow<FoodEntity?>

    /**
     * Retrieves food by ID synchronously for one-time operations
     *
     * Used in toggleFavorite to get current state before updating.
     * For reactive UI updates, use getFoodById() Flow variant.
     */
    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFoodByIdSync(foodId: Int): FoodEntity?

    @Query("SELECT * FROM foods WHERE categoryId = :categoryId")
    fun getFoodsByCategory(categoryId: Int): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE name LIKE '%' || :query || '%'")
    fun searchFoods(query: String): Flow<List<FoodEntity>>

    /**
     * Updates food entity in database, triggering Flow updates
     *
     * Used for favorite toggle persistence. Room automatically
     * notifies all Flow observers when data changes.
     */
    @Update
    suspend fun updateFood(food: FoodEntity)

    @Query("DELETE FROM foods")
    suspend fun deleteAll()
}