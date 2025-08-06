package com.example.recipefinder.ui.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefinder.R
import com.example.recipefinder.ui.components.GenericDialog
import com.example.recipefinder.ui.components.PositiveDialogButton
import com.example.recipefinder.ui.components.PrimaryButton
import com.example.recipefinder.ui.components.ProgressOverlay
import com.example.recipefinder.ui.components.RecipeCard
import com.example.recipefinder.ui.components.SearchBar
import com.example.recipefinder.ui.theme.AppColors
import com.example.recipefinder.ui.theme.AppTextStyles
import com.example.recipefinder.ui.utils.text.emptyFieldValidator
import com.example.recipefinder.ui.utils.text.isFormValid
import com.example.recipefinder.ui.utils.text.minLengthValidator
import com.example.recipefinder.ui.utils.text.rememberFieldState
import com.example.recipefinder.utils.anyLoading
import com.example.recipefinder.utils.data
import com.example.recipefinder.utils.ifFailed
import com.example.recipefinder.utils.isSuccess
import com.example.recipefinder.utils.withoutEmoji
import kotlinx.coroutines.launch


@Composable
fun RecipesScreen(
    onRecipeClick: (RecipeItemViewModel) -> Unit,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val recipeState by viewModel.recipesFlow.collectAsState()
    val favoriteRecipeState by viewModel.favoriteRecipesFlow.collectAsState()

    val searchFieldState = rememberFieldState(
        valuePreprocessor = { _, newValue ->
            val value = newValue.withoutEmoji()
            value
        },
        validator = emptyFieldValidator(R.string.search_field_empty_error)
                + minLengthValidator(R.string.search_field_min_length_error, 3),
        lazyValidate = false
    )

    val searchFieldFocusRequester = remember { FocusRequester() }
    val query = searchFieldState.value
    val showFavorites = query.isBlank()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        containerColor = AppColors.Primary
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            SearchBar(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                fieldState = searchFieldState,
                onSearchIconClick = {
                    if (isFormValid(searchFieldState)) {
                        viewModel.searchRecipes(searchFieldState.value)
                    }
                },
                searchFieldFocusRequester = searchFieldFocusRequester
            )

            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(
                    if (showFavorites) R.string.favorites_title else R.string.results_suggested_recipes
                ),
                style = AppTextStyles.bold,
            )

            ProgressOverlay(
                modifier = Modifier.fillMaxSize(),
                loading = anyLoading(recipeState, favoriteRecipeState),
            ) {
                val recipesToShow = if (showFavorites) {
                    if (favoriteRecipeState.isSuccess) favoriteRecipeState.data else emptyList<RecipeItemViewModel>()
                } else {
                    if (recipeState.isSuccess) recipeState.data else emptyList<RecipeItemViewModel>()
                }

                recipesToShow.let { recipes ->
                    LazyColumn(
                        modifier = Modifier.padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = recipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            RecipeCard(
                                modifier = Modifier.padding(bottom = 16.dp),
                                recipe = recipe,
                                onClick = { onRecipeClick(recipe) },
                                onFavoriteClick = {
                                    coroutineScope.launch {
                                        viewModel.onFavoriteClick(recipe)
                                    }
                                }
                            )
                        }

                        item {
                            if (!showFavorites) {
                                PrimaryButton(
                                    modifier = Modifier
                                        .padding(top = 20.dp),
                                    text = stringResource(R.string.results_no_like_recipes_btn_text),
                                    onClick = {
                                        viewModel.searchRecipes(searchFieldState.value)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        recipeState.ifFailed {
            GenericDialog(
                onDismissRequest = {
                    viewModel.searchRecipes(searchFieldState.value)
                },
                title = stringResource(R.string.search_error_title),
                message = stringResource(R.string.search_error_message_default),
                positiveButton = PositiveDialogButton(
                    text = stringResource(R.string.ok),
                    onClick = {
                        viewModel.searchRecipes(searchFieldState.value)
                    }
                )
            )
        }
    }
}

