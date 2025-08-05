package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipefinder.ui.theme.AppColors

@Composable
fun Loader(
    modifier: Modifier = Modifier,
    color: Color = AppColors.MainAccent
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            color = color
        )
    }
}

@Composable
fun ProgressOverlay(
    loading: Boolean,
    modifier: Modifier = Modifier,
    overlayColor: Color = AppColors.Primary,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        content()

        if (loading) {
            Loader(
                modifier = Modifier
                    .matchParentSize()
                    .background(overlayColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
            )
        }
    }
}