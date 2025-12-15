package com.borayildirim.foodiehub.domain.repository

import com.borayildirim.foodiehub.domain.model.Category
import com.borayildirim.foodiehub.domain.model.Food
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for food catalog operations
 *
 * Provides food browsing, search, and favorite management with reactive
 * Flow queries for automatic UI synchronization. Combines Room database
 * for persistent data with MockFoodData for UI-specific content.
 *
 * Architecture:
 * - Room database: Core food data and favorite status
 * - MockFoodData: UI strings and customization options
 * - Flow-based: Reactive updates on data changes
 *
 * Data strategy:
 * - Favorites persist in database across app restarts
 * - Categories are hardcoded (no database storage needed)
 * - Customization options from MockFoodData (avoid DB bloat)
 */
interface FoodRepository {
    /**
     * Retrieves all food items with reactive updates
     *
     * Merges database entities with MockFoodData for complete food details.
     * Emits new list whenever food data or favorites change.
     *
     * @return Flow emitting complete food list
     */
    fun getFoods(): Flow<List<Food>>

    /**
     * Retrieves specific food by ID with reactive updates
     *
     * Returns complete food with customization options for detail screen.
     * Emits updates when food or favorite status changes.
     *
     * @param foodId Food identifier
     * @return Flow emitting food or null if not found
     */
    fun getFood(foodId: Int): Flow<Food?>

    /**
     * Retrieves food categories for filter UI
     *
     * Returns hardcoded category list (no database query).
     * Categories are static and don't require persistence.
     *
     * @return Flow emitting category list with "All" selected by default
     */
    fun getCategories(): Flow<List<Category>>

    /**
     * Retrieves user's favorite foods with reactive updates
     *
     * Filters complete food list by favorite status. Emits new list
     * whenever favorites are toggled or food data changes.
     *
     * @return Flow emitting favorite foods only
     */
    fun getFavoriteFoods(): Flow<List<Food>>

    /**
     * Searches foods by name query with reactive updates
     *
     * Performs case-insensitive partial match on food names using
     * SQL LIKE operator. Merges results with MockFoodData.
     *
     * @param query Search term (partial match supported)
     * @return Flow emitting matching foods
     */
    fun searchFoods(query: String): Flow<List<Food>>

    /**
     * Toggles food favorite status with database persistence
     *
     * Updates isFavorite flag in database, triggering reactive Flow
     * updates across all screens observing food data. Changes persist
     * across app restarts.
     *
     * @param foodId Food identifier to toggle
     */
    suspend fun toggleFavorite(foodId: Int)
}