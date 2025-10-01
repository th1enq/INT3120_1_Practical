package com.example.mycity.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mycity.R

val JosefinSans = FontFamily(
    Font(R.font.josefin_sans_semibold, FontWeight.SemiBold),
    Font(R.font.josefin_sans_medium, FontWeight.Medium)
)
val Gabarito= FontFamily(
    Font(R.font.gabarito_regular),
    Font(R.font.gabarito_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Gabarito,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Gabarito,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)