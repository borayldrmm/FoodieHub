package com.borayildirim.foodiehub.presentation.ui.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


/**
 * Custom shape for bottom navigation bar with smooth notched cutout.
 *
 * Creates a bottom bar with rounded top corners and a smooth curved notch
 * in the center for floating action button integration. The shape uses
 * cubic bezier curves for smooth shoulder transitions and maintains
 * responsive proportions based on provided parameters.
 *
 * Features:
 * - Responsive notch sizing based on FAB requirements
 * - Smooth shoulder curves with configurable easing
 * - Top-only corner rounding for modern design
 * - Performance-optimized path calculations
 *
 * @param notchWidthDp Width of the notch cutout area in density-independent pixels
 * @param notchDepthDp Depth of the notch curve extending into the bar
 * @param shoulderEase Shoulder curve smoothness factor [0f..1f] - higher values create more curved shoulders
 * @param topCornerRadiusDp Corner radius applied only to top corners for visual hierarchy
 *
 * @throws IllegalArgumentException if shoulderEase is not in range [0f..1f]
 * @throws IllegalArgumentException if notchWidthDp or notchDepthDp are not positive
 * @see Shape Compose UI Shape interface
 */
class BottomBarNotchedShape(
    private val notchWidthDp: Dp = 140.dp,   // Notch width
    private val notchDepthDp: Dp = 40.dp,    // Notch depth
    private val shoulderEase: Float = 0.25f, // Shoulder fold (0.20–0.45)
    private val topCornerRadiusDp: Dp = 28.dp
) : Shape {

    // Parameter validation for runtime safety
    init {
        require(shoulderEase in 0f..1f) {
            "shoulderEase must be between 0f and 1f, got: $shoulderEase"
        }
        require(notchWidthDp > 0.dp) {
            "notchWidthDp must be positive, got: $notchWidthDp"
        }
        require(notchDepthDp > 0.dp) {
            "notchDepthDp must be positive, got: $notchDepthDp"
        }
        require(topCornerRadiusDp >= 0.dp) {
            "topCornerRadiusDp must be non-negative, got: $topCornerRadiusDp"
        }
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(createPath(size, density))
    }

    /**
     * Creates the path for the notched bottom bar shape.
     *
     * Path construction order:
     * 1. Start from top-left corner with radius
     * 2. Draw top edge to notch start
     * 3. Create smooth notch with cubic bezier curves
     * 4. Complete top edge to top-right corner
     * 5. Draw right edge, bottom edge, and left edge (straight)
     *
     * @param size Available drawing area size
     * @param density Current screen density for dp-to-px conversion
     * @return Path representing the complete shape outline
     */
    private fun createPath(size: Size, density: Density): Path {
        val width = size.width
        val height = size.height

        // Convert density-independent pixels to actual pixels
        val cornerRadius = with(density) { topCornerRadiusDp.toPx() }
        val notchWidth = with(density) { notchWidthDp.toPx() }
        val notchDepth = with(density) { notchDepthDp.toPx() }

        // Calculate center and notch boundaries
        val centerX = width / 2f
        val notchStartX = centerX - notchWidth / 2f
        val notchEndX = centerX + notchWidth / 2f

        // Calculate shoulder easing distance for smooth curves
        val shoulderDistance = notchWidth * shoulderEase

        return Path().apply {
            // Start path at top-left corner beginning
            moveTo(cornerRadius, 0f)

            // Top-left corner arc (quarter circle)
            if (cornerRadius > 0f) {
                arcTo(
                    rect = Rect(0f, 0f, 2 * cornerRadius, 2 * cornerRadius),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }

            // Top edge: straight line to notch start
            lineTo(notchStartX, 0f)

            // Notch creation with smooth cubic bezier curves
            // Left shoulder: smooth transition into notch
            cubicTo(
                x1 = notchStartX + shoulderDistance, y1 = 0f,           // Control point 1
                x2 = centerX - shoulderDistance, y2 = notchDepth,       // Control point 2
                x3 = centerX, y3 = notchDepth                           // End point (center bottom)
            )

            // Right shoulder: smooth transition out of notch
            cubicTo(
                x1 = centerX + shoulderDistance, y1 = notchDepth,       // Control point 1
                x2 = notchEndX - shoulderDistance, y2 = 0f,             // Control point 2
                x3 = notchEndX, y3 = 0f                                 // End point (notch end)
            )

            // Top edge: continue to top-right corner
            lineTo(width - cornerRadius, 0f)

            // Top-right corner arc (quarter circle)
            if (cornerRadius > 0f) {
                arcTo(
                    rect = Rect(width - 2 * cornerRadius, 0f, width, 2 * cornerRadius),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }

            // Right edge: straight line down (no bottom corners)
            lineTo(width, height)

            // Bottom edge: straight line across
            lineTo(0f, height)

            // Left edge: straight line up to corner start
            lineTo(0f, cornerRadius)

            // Close path to complete shape
            close()
        }
    }

    companion object {
        /**
         * Default shape instance with standard parameters.
         * Suitable for most bottom navigation implementations.
         */
        val Default = BottomBarNotchedShape()

        /**
         * Compact shape instance with smaller notch for narrow screens.
         */
        val Compact = BottomBarNotchedShape(
            notchWidthDp = 100.dp,
            notchDepthDp = 30.dp,
            shoulderEase = 0.2f,
            topCornerRadiusDp = 20.dp
        )

        /**
         * Wide shape instance with larger notch for tablet layouts.
         */
        val Wide = BottomBarNotchedShape(
            notchWidthDp = 180.dp,
            notchDepthDp = 50.dp,
            shoulderEase = 0.3f,
            topCornerRadiusDp = 32.dp
        )
    }
}