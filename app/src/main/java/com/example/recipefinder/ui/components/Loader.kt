package com.example.recipefinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.example.recipefinder.ui.theme.AppColors

@Composable
fun ProgressOverlay(
    loading: Boolean,
    animationComposition: LottieComposition?,
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
                    ),
                animationComposition = animationComposition
            )
        }
    }
}

@Composable
private fun Loader(
    modifier: Modifier = Modifier,
    animationComposition: LottieComposition?,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = animationComposition,
            iterations = LottieConstants.IterateForever
        )
    }
}