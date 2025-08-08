package com.borayildirim.foodiehub.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.borayildirim.foodiehub.R

// Local Font Setup (Manual Download)
val LobsterFontFamily = FontFamily(
    Font(R.font.lobster_regular, FontWeight.Normal)
)

// Extension Properties for Custom Typography
val Typography.splashTitle: TextStyle
    get() = TextStyle(
        fontFamily = LobsterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp,
        color = SplashTextColor
    )

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)