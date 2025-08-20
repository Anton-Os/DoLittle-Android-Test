package com.zeroplus.dolittle.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zeroplus.dolittle.R

// Set of Material typography styles to start with
val typography = Typography(
    // Body
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.century_gothic)),
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.century_gothic)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.century_gothic)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Title
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.josefin_slab)),
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.josefin_slab)),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // Label
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.semi_coder)),
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.semi_coder)),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)