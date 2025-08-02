package com.example.recipefinder.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun RecipeFinderTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = AppColors.Primary,
            onPrimary = AppColors.OnPrimary,
            primaryContainer = AppColors.PrimaryContainer,
            onPrimaryContainer = AppColors.OnPrimaryContainer,
            secondary = AppColors.Secondary,
            onSecondary = AppColors.OnSecondary,
            tertiary = AppColors.Tertiary,
            onTertiary = AppColors.Tertiary,
            background = AppColors.Background,
            surface = AppColors.Surface,
            onSurface = AppColors.OnSurface,
            error = AppColors.Error,
            surfaceContainer = AppColors.Primary,
            surfaceContainerLow = AppColors.Primary,
            surfaceContainerHigh = AppColors.Primary,
            surfaceContainerLowest = AppColors.Primary,
            surfaceContainerHighest = AppColors.Primary
        ),
        typography = Typography,
    ) {
        content()
    }
}