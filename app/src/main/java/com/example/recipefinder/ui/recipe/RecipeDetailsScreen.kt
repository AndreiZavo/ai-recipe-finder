package com.example.recipefinder.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.recipefinder.R
import com.example.recipefinder.ui.components.AnnotatedList
import com.example.recipefinder.ui.components.AsyncImageWrapper
import com.example.recipefinder.ui.components.FavoriteIconButton
import com.example.recipefinder.ui.components.GenericDialog
import com.example.recipefinder.ui.components.ListType
import com.example.recipefinder.ui.components.PositiveDialogButton
import com.example.recipefinder.ui.components.ProgressOverlay
import com.example.recipefinder.ui.components.StatusBarsAppearance
import com.example.recipefinder.ui.recipes.RecipesViewModel
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.utils.ShowOnAppearanceToolbar
import com.example.recipefinder.ui.utils.formatDuration
import com.example.recipefinder.utils.ifFailed
import com.example.recipefinder.utils.ifSuccess
import com.example.recipefinder.utils.isLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    onBackClick: () -> Unit,
    recipeViewModel: RecipesViewModel
) {
    val selectedRecipe by recipeViewModel.selectedRecipeFlow.collectAsState()

    val screenHeight = LocalConfiguration.current.screenHeightDp

    val scrollState = rememberScrollState()
    val showTopBar by remember { derivedStateOf { scrollState.value > screenHeight / 2 } }

    val configuration = LocalConfiguration.current
    val headerHeight = remember { configuration.screenHeightDp.dp * .5f }

    val animationComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_ai_star_loading)
    )

    StatusBarsAppearance(lightStatusBars = showTopBar)

    LaunchedEffect(Unit) {
        recipeViewModel.loadSelectedRecipe(recipeId)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(top = 0.dp),
    ) { paddingValues ->
        ProgressOverlay(
            modifier = Modifier.fillMaxSize(),
            loading = selectedRecipe.isLoading,
            animationComposition = animationComposition
        ) {
            selectedRecipe.ifSuccess { recipe ->
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
                            imageUrl = recipe.imageUrl,
                            placeholder = painterResource(R.drawable.img_placeholder),
                            contentDescription = stringResource(R.string.recipe_details_header),
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
                                text = recipe.title,
                                style = AppTextStyles.semibold,
                                fontSize = 24.sp,
                                color = AppColors.TextPrimary
                            )

                            Text(
                                text = formatDuration(recipe.duration),
                                style = AppTextStyles.regular,
                                fontSize = 14.sp,
                                color = AppColors.TextPrimary
                            )
                        }

                        FavoriteIconButton(
                            isFavorite = recipe.isFavorite,
                            onClick = {
                                recipe.toggleFavorite()
                                recipeViewModel.onFavoriteClick(recipe)
                                recipeViewModel.loadSelectedRecipe(recipe.id)
                            }
                        )
                    }

                    AnnotatedList(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .padding(horizontal = 16.dp),
                        title = stringResource(R.string.recipe_details_ingredients_title),
                        items = recipe.ingredients,
                        listType = ListType.UNORDERED
                    )

                    AnnotatedList(
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .padding(horizontal = 16.dp),
                        title = stringResource(R.string.recipe_details_instructions_title),
                        items = recipe.instructions,
                        listType = ListType.ORDERED
                    )

                    Spacer(modifier = Modifier.height(120.dp))
                }

                ShowOnAppearanceToolbar(
                    onBackClick = onBackClick,
                    show = showTopBar,
                    title = recipe.title,
                )
            }
        }
    }

    selectedRecipe.ifFailed {
        GenericDialog(
            onDismissRequest = onBackClick,
            title = stringResource(R.string.search_error_title),
            message = stringResource(R.string.search_error_message_default),
            positiveButton = PositiveDialogButton(
                text = stringResource(R.string.results_ok),
                onClick = onBackClick
            )
        )
    }
}
