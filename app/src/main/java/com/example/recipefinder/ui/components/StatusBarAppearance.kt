package com.example.recipefinder.ui.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.recipefinder.ui.theme.AppColors

@Composable
fun StatusBarsAppearance(
    statusBarsColor: Color = AppColors.Transparent,
    lightStatusBars: Boolean = true
) {
    val updatedStatusBarLight by rememberUpdatedState(newValue = lightStatusBars)
    val updatedStatusBarColor by rememberUpdatedState(newValue = statusBarsColor)

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = updatedStatusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                updatedStatusBarLight
        }
    }
}