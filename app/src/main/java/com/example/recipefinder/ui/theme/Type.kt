package com.example.recipefinder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R

private val outfitFamily = FontFamily(
    Font(R.font.outfit_regular, FontWeight.Normal),
    Font(R.font.outfit_bold, FontWeight.Bold),
    Font(R.font.outfit_semibold, FontWeight.SemiBold),
    Font(R.font.outfit_thin, FontWeight.Thin),
)

val FontFamily.Companion.Outfit by lazy {
    outfitFamily
}

object AppTextStyles {
    val regular by lazy {
        TextStyle(
            fontFamily = FontFamily.Outfit,
            fontWeight = FontWeight.Normal,
            color = AppColors.TextPrimary,
            fontSize = 16.sp
        )
    }

    val thin by lazy {
        TextStyle(
            fontFamily = FontFamily.Outfit,
            fontWeight = FontWeight.Thin,
            color = AppColors.TextPrimary,
            fontSize = 16.sp
        )
    }

    val semibold by lazy {
        TextStyle(
            fontFamily = FontFamily.Outfit,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            color = AppColors.TextPrimary,
            fontSize = 16.sp
        )
    }

    val bold by lazy {
        TextStyle(
            fontFamily = FontFamily.Outfit,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextPrimary,
            fontSize = 32.sp
        )
    }
}

// Set of Material typography styles to start with
val Typography = Typography()