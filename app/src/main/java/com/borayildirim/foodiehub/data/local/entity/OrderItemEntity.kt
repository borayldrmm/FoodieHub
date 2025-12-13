package com.borayildirim.foodiehub.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for order line items with cascade delete
 *
 * Stores individual food items within orders using foreign key relationship.
 * When parent order is deleted, all associated items are automatically removed
 * via CASCADE delete for referential integrity.
 *
 * Data denormalization strategy:
 * - Stores food details snapshot (name, image, price) at order time
 * - Preserves historical data even if menu items change or are deleted
 * - Lists serialized as comma-separated strings for simple storage
 *
 * @property id Unique line item identifier (UUID)
 * @property orderId Foreign key to parent order with CASCADE delete
 * @property productId Food ID at order time (historical reference)
 * @property productName Food name snapshot for order history
 * @property productImage Image resource ID snapshot
 * @property quantity Number of this item ordered
 * @property price Unit price at order time (historical pricing)
 * @property spicyLevel Selected spice level (0.0 to 1.0)
 * @property selectedToppings Comma-separated topping names
 * @property selectedSides Comma-separated side option names
 */
@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("orderId")]
)
data class OrderItemEntity(
    @PrimaryKey val id: String,
    val orderId: String,
    val productId: Int,
    val productName: String,
    val productImage: String,
    val quantity: Int,
    val price: Double,
    val spicyLevel: Float,
    val selectedToppings: String,
    val selectedSides: String
)