package com.borayildirim.foodiehub.presentation.ui.components.address

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.model.AddressType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * A swipeable address card component that displays address details and supports
 * swipe-to-delete functionality.
 *
 * Features:
 * - Swipe left to reveal delete action
 * - Visual feedback with gradient bar based on address type
 * - Default address badge
 * - Set as default functionality
 * - Click to edit
 *
 * @param address The address data to display
 * @param onDeleteClick Callback invoked when delete button is clicked
 * @param onSetDefaultClick Callback invoked when set default button is clicked
 * @param onEditClick Callback invoked when card is clicked
 * @param modifier Optional modifier for the card container
 */
@Composable
fun AddressCard(
    address: Address,
    onDeleteClick: () -> Unit,
    onSetDefaultClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeState = rememberSwipeState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // Background layer: Delete button revealed on swipe
        SwipeBackground(
            isVisible = swipeState.offsetX.value < -10f,
            onDeleteClick = {
                swipeState.reset()
                onDeleteClick()
            }
        )

        // Foreground layer: Address card content
        AddressCardContent(
            address = address,
            swipeState = swipeState,
            onEditClick = onEditClick,
            onSetDefaultClick = onSetDefaultClick
        )
    }

    // Reset swipe state when card is removed from composition
    DisposableEffect(Unit) {
        onDispose { swipeState.resetImmediate() }
    }
}

/**
 * Background layer that appears when card is swiped left.
 * Contains delete button with red background.
 *
 * @param isVisible Whether the background should be visible
 * @param onDeleteClick Callback when delete button is clicked
 */
@Composable
private fun BoxScope.SwipeBackground(
    isVisible: Boolean,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .matchParentSize() // Match exact parent Box size (card height)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isVisible) MaterialTheme.colorScheme.onPrimary
                else Color.Transparent
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (isVisible) {
            // Larger clickable delete area
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp), // Full swipe area width
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(48.dp) // Larger button
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = Color.White,
                        modifier = Modifier.size(32.dp) // Larger icon
                    )
                }
            }
        }
    }
}

/**
 * Main card content with swipe gesture handling.
 *
 * @param address Address data to display
 * @param swipeState State manager for swipe gestures
 * @param onEditClick Callback when card is clicked
 * @param onSetDefaultClick Callback when set default button is clicked
 */
@Composable
private fun AddressCardContent(
    address: Address,
    swipeState: SwipeState,
    onEditClick: () -> Unit,
    onSetDefaultClick: () -> Unit
) {
    ElevatedCard(
        onClick = onEditClick,
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(swipeState.offsetX.value.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = { swipeState.onDragEnd() },
                    onHorizontalDrag = { _, dragAmount ->
                        swipeState.onDrag(dragAmount)
                    }
                )
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddressGradientBar(type = address.addressType)
            AddressInfo(address = address, modifier = Modifier.weight(1f))

            SetDefaultButton(
                isDefault = address.isDefault,
                onClick = onSetDefaultClick
            )
        }
    }
}

/**
 * Button to set this address as default.
 * Shows filled star if default, outlined star if not.
 *
 * @param isDefault Whether this is the default address
 * @param onClick Callback when button is clicked
 */
@Composable
private fun SetDefaultButton(
    isDefault: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp),
        enabled = !isDefault // ← Disable if already default
    ) {
        Icon(
            imageVector = if (isDefault) Icons.Default.Star else Icons.Default.StarBorder,
            contentDescription = stringResource(R.string.set_as_default),
            tint = if (isDefault) {
                MaterialTheme.colorScheme.onPrimary // ← Red (if Default)
            } else {
                MaterialTheme.colorScheme.primary // ← Purple (if not Default)
            },
            modifier = Modifier.size(28.dp)
        )
    }
}

/**
 * Vertical gradient bar displayed on the left side of the card.
 * Color varies based on address type (Home/Work/Other).
 *
 * @param type Address type determining gradient colors
 */
@Composable
private fun AddressGradientBar(type: AddressType) {
    Box(
        modifier = Modifier
            .width(4.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(getGradientForType(type))
    )
}

/**
 * Displays address information including title, full address, location, and phone.
 *
 * @param address Address data to display
 * @param modifier Modifier for the column container
 */
@Composable
private fun AddressInfo(
    address: Address,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AddressTitleRow(
            type = address.addressType,
            title = address.title,
            isDefault = address.isDefault
        )

        Text(
            text = address.fullAddress,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "${address.district}, ${address.city}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "📱 ${formatPhoneNumber(address.phoneNumber)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Title row with icon, title text, and optional default badge.
 *
 * @param type Address type for icon selection
 * @param title Address title text
 * @param isDefault Whether this is the default address
 */
@Composable
private fun AddressTitleRow(
    type: AddressType,
    title: String,
    isDefault: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = getIconForType(type),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        if (isDefault) {
            DefaultBadge()
        }
    }
}

/**
 * Badge indicating this is the default address.
 */
@Composable
private fun DefaultBadge() {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = stringResource(R.string.default_txt),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

/**
 * Remembers and creates a SwipeState for managing swipe gestures.
 *
 * @return SwipeState instance with 120dp max swipe distance
 */
@Composable
private fun rememberSwipeState(): SwipeState {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    return remember {
        SwipeState(
            maxSwipeDistance = with(density) { -120.dp.toPx() },
            scope = scope
        )
    }
}

/**
 * State management class for swipe gestures.
 * Handles drag events, snap behavior, and animations.
 *
 * @property maxSwipeDistance Maximum distance card can be swiped (negative value for left swipe)
 * @property scope Coroutine scope for launching animations
 */
private class SwipeState(
    private val maxSwipeDistance: Float,
    private val scope: CoroutineScope
) {
    val offsetX = Animatable(0f)

    /**
     * Handles ongoing drag gesture.
     * Constrains drag within [maxSwipeDistance, 0f] range.
     *
     * @param dragAmount Horizontal drag distance in pixels
     */
    fun onDrag(dragAmount: Float) {
        scope.launch {
            val newValue = (offsetX.value + dragAmount).coerceIn(maxSwipeDistance, 0f)
            offsetX.snapTo(newValue)
        }
    }

    /**
     * Handles end of drag gesture.
     * Snaps to either fully open (maxSwipeDistance) or closed (0f)
     * based on current position relative to midpoint.
     */
    fun onDragEnd() {
        scope.launch {
            val targetValue = if (offsetX.value < maxSwipeDistance / 2) {
                maxSwipeDistance // Snap to open
            } else {
                0f // Snap to closed
            }
            offsetX.animateTo(targetValue, animationSpec = tween(200))
        }
    }

    /**
     * Animates card back to closed position.
     * Used after delete button is clicked.
     */
    fun reset() {
        scope.launch {
            offsetX.animateTo(0f, animationSpec = tween(300))
        }
    }

    /**
     * Immediately resets card to closed position without animation.
     * Used when card is removed from composition.
     */
    fun resetImmediate() {
        scope.launch {
            offsetX.snapTo(0f)
        }
    }
}

/**
 * Formats phone number to Turkish format: 0 (5XX) XXX XX XX
 *
 * @param phone Raw phone number string
 * @return Formatted phone number or original if invalid format
 */
private fun formatPhoneNumber(phone: String): String {
    val digits = phone.filter { it.isDigit() }
    return when {
        digits.length >= 10 -> {
            val cleaned = if (digits.startsWith("0")) digits.substring(1) else digits
            "0 (${cleaned.substring(0, 3)}) ${cleaned.substring(3, 6)} ${cleaned.substring(6, 8)} ${cleaned.substring(8, 10)}"
        }
        else -> phone
    }
}

/**
 * Returns gradient brush based on address type.
 *
 * @param type Address type (Home/Work/Other)
 * @return Vertical gradient brush with type-specific colors
 */
@Composable
private fun getGradientForType(type: AddressType): Brush {
    return when (type) {
        AddressType.HOME -> Brush.verticalGradient(
            colors = listOf(Color(0xFF2196F3), Color(0xFF00BCD4))
        )
        AddressType.WORK -> Brush.verticalGradient(
            colors = listOf(Color(0xFFFF9800), Color(0xFFFF5722))
        )
        AddressType.OTHER -> Brush.verticalGradient(
            colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63))
        )
    }
}

/**
 * Returns icon based on address type.
 *
 * @param type Address type (Home/Work/Other)
 * @return Material icon for the address type
 */
@Composable
private fun getIconForType(type: AddressType): ImageVector {
    return when (type) {
        AddressType.HOME -> Icons.Default.Home
        AddressType.WORK -> Icons.Default.Work
        AddressType.OTHER -> Icons.Default.Star
    }
}