package com.example.recipefinder.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.R
import com.example.recipefinder.ui.components.AsyncImageWrapper
import com.example.recipefinder.ui.components.FavoriteIconButton
import com.example.recipefinder.ui.components.OrderedList
import com.example.recipefinder.ui.components.StatusBarsAppearance
import com.example.recipefinder.ui.components.UnorderedList
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.utils.ShowOnAppearanceToolbar
import com.example.recipefinder.ui.utils.formatDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeTitle: String,
    recipeDuration: Int,
    recipeImageUrl: String,
    recipeIngredients: List<String>,
    recipeInstructions: List<String>,
    onBackClick: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val scrollState = rememberScrollState()
    val showTopBar by remember { derivedStateOf { scrollState.value > screenHeight / 2 } }

    val configuration = LocalConfiguration.current
    val headerHeight = remember { configuration.screenHeightDp.dp * .5f }

    StatusBarsAppearance(lightStatusBars = showTopBar)

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0.dp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Box {
                AsyncImageWrapper(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight),
                    imageUrl = recipeImageUrl,
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 15.dp)
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = recipeTitle,
                        style = AppTextStyles.semibold,
                        fontSize = 24.sp,
                        color = AppColors.TextPrimary
                    )

                    Text(
                        text = formatDuration(recipeDuration),
                        style = AppTextStyles.regular,
                        fontSize = 14.sp,
                        color = AppColors.TextPrimary
                    )
                }

                FavoriteIconButton(
                    modifier = Modifier.padding(end = 16.dp),
                    isFavorite = false,
                    onClick = {}
                )
            }

            UnorderedList(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(horizontal = 16.dp),
                title = stringResource(R.string.recipe_details_ingredients_title),
                items = recipeIngredients
            )

            OrderedList(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .padding(horizontal = 16.dp),
                title = stringResource(R.string.recipe_details_instructions_title),
                items = recipeInstructions
            )

            Spacer(modifier = Modifier.height(120.dp))
        }

        ShowOnAppearanceToolbar(
            onBackClick = onBackClick,
            show = showTopBar,
            title = recipeTitle
        )
    }
}
