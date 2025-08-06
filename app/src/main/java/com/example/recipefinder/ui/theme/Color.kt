package com.example.recipefinder.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.ui.graphics.Color

class AppColors {
    companion object {
        val Primary = Color(0xFFFFFFFF)
        val OnPrimary = Color(0xFF1A1A1A)
        val Secondary = Color(0xFFF4F4F4)
        val OnSecondary = Primary
        val Tertiary = Color(0xFFFFFFFF)
        val PrimaryContainer = Color(0xFFF7F7F7)
        val OnPrimaryContainer = Color(0xFF909090)
        val Background = Color(0xFFFFFFFF)
        val Surface = Color(0xFFFFFFFF)
        val OnSurface = Color(0xFFFFFFFF)
        val Error = Color(0xFFFF0700)
        val Transparent = Color(0x00000000)

        //texts
        val TextPrimary = Color(0xFF000000)
        val TextSecondary = Color(0xFFF5F5F5)
        val TextPlaceholder = Color(0xFFB3B3B3)
        //Tags
        val IconTint = Color(0xFF65558F)
        val MainAccent = Color(0xFF2A2850)
        val MainAccent10 = Color(0x1A2A2850)
        val PrimaryButton = Color(0xFF65558F)
        val ButtonDisabled = Color(0xFFACAEB3)
        val BorderPrimary = Color(0xFFD9D9D9)
        val BorderSecondary = Color(0xFF000000)
        val Shadow = Color(0xFFE1E1E1)

        //Divider
        val Divider = Color(0xFFF4F4F4)

        val TextSelectionColors = TextSelectionColors(
            handleColor = MainAccent,
            backgroundColor = MainAccent10
        )
    }
}