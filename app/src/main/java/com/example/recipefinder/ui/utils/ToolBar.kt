package com.example.recipefinder.ui.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier
            .shadow(
                elevation = 0.dp,
                spotColor = AppColors.OnSurface
            )
            .fillMaxWidth(),
        navigationIcon = {
            navigationIcon()
        },
        title = title,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowOnAppearanceToolbar(
    show: Boolean,
    onBackClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    val scrollAlpha by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(durationMillis = 200)
    )

    val animatedBackgroundColor by animateColorAsState(
        targetValue = AppColors.Primary.copy(alpha = scrollAlpha),
        animationSpec = tween(
            durationMillis = 200,
            easing = if (show) FastOutSlowInEasing else LinearOutSlowInEasing
        )
    )

    val animatedTitleAlpha by animateFloatAsState(
        targetValue = scrollAlpha,
        animationSpec = tween(
            durationMillis = 200,
            easing = if (show) FastOutSlowInEasing else LinearOutSlowInEasing
        )
    )

    Toolbar(
        modifier = modifier
            .fillMaxWidth()
            .background(animatedBackgroundColor)
            .statusBarsPadding(),
        title = {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .alpha(animatedTitleAlpha),
                text = title,
                style = AppTextStyles.bold.copy(fontSize = 16.sp)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    tint = AppColors.TextPrimary,
                )
            }
        },
        actions = {},
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Color.Transparent,
            scrolledContainerColor = animatedBackgroundColor
        ),
    )
}
