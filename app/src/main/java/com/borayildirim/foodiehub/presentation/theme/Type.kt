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

val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium)
)

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto, FontWeight.Medium)
)

// Extension Properties for Custom Typography
val Typography.appNameTitle: TextStyle
    get() = TextStyle(
        fontFamily = LobsterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
        lineHeight = 56.sp,
        letterSpacing = 0.sp,
        color = SplashTextColor
    )

val Typography.homeSubtitle: TextStyle
    get() = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = HomeSubtitleColor
    )

val Typography.robotoTxt: TextStyle
    get() = TextStyle(
        fontFamily = RobotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = HomeSubtitleColor
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