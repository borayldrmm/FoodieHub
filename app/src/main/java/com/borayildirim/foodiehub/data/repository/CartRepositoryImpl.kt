package com.borayildirim.foodiehub.data.repository

import com.borayildirim.foodiehub.data.local.dao.CartDao
import com.borayildirim.foodiehub.data.local.dao.FoodDao
import com.borayildirim.foodiehub.data.local.mapper.toDomain
import com.borayildirim.foodiehub.data.local.mapper.toEntity
import com.borayildirim.foodiehub.domain.model.CartItem
import com.borayildirim.foodiehub.domain.model.MockFoodData
import com.borayildirim.foodiehub.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CartRepository implementation combining cart items with food data
 *
 * Uses Flow combine to reactively merge cart entities with food details,
 * ensuring cart UI updates when either cart or food data changes.
 */
@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val foodDao: FoodDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return combine(
            cartDao.getAllCartItems(),
            foodDao.getAllFoods()
        ) { cartEntities, foodEntities ->
            cartEntities.mapNotNull { cartEntity ->
                val foodEntity = foodEntities.find { it.id == cartEntity.foodId }
                foodEntity?.let {
                    val mockFood = MockFoodData.getAllFoods().find { it.id == foodEntity.id }
                    val food = foodEntity.toDomain(
                        description = mockFood?.description,
                        detailedDescription = mockFood?.detailedDescription,
                        availableToppings = mockFood?.availableToppings ?: emptyList(),
                        availableSideOptions = mockFood?.availableSideOptions ?: emptyList()
                    )
                    cartEntity.toDomain(food)
                }
            }
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem.toEntity())
    }

    override suspend fun removeFromCart(itemId: String) {
        val cartItem = cartDao.getCartItemById(itemId).first()
        cartItem?.let {
            cartDao.deleteCartItem(it)
        }
    }

    override suspend fun updateQuantity(itemId: String, quantity: Int) {
        val cartItem = cartDao.getCartItemById(itemId).first()
        cartItem?.let {
            cartDao.updateCartItem(it.copy(quantity = quantity))
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}