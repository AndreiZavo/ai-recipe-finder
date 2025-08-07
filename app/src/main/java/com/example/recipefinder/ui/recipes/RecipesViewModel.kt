package com.example.recipefinder.ui.recipes

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.data.local.FavoriteSerializer
import com.example.recipefinder.data.models.FavoriteRecipes
import com.example.recipefinder.data.services.GeminiService
import com.example.recipefinder.ui.base.BaseViewModel
import com.example.recipefinder.utils.DataState
import com.example.recipefinder.utils.data
import com.example.recipefinder.utils.dataFlowOf
import com.example.recipefinder.utils.dataOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import javax.inject.Inject

val Context.dataStore: DataStore<FavoriteRecipes> by dataStore(
    fileName = "app-settings.json",
    serializer = FavoriteSerializer
)

data class RecipeInfo(
    val searchedRecipes: List<RecipeItemViewModel>,
    val favoriteRecipes: List<RecipeItemViewModel>,
    val displayedRecipes: List<RecipeItemViewModel>,
    val isDisplayingSearchResults: Boolean = false,
)

@HiltViewModel
class RecipesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val geminiService: GeminiService,
) : BaseViewModel<Unit>() {

    val recipesFlow = MutableStateFlow<DataState<RecipeInfo>>(DataState.Uninitialized)
    val selectedRecipeFlow = dataFlowOf<RecipeItemViewModel>()

    private val appDataStore: DataStore<FavoriteRecipes> = context.dataStore

    init {
        loadFavoriteRecipes()
    }

    fun searchRecipes(query: String) {
        recipesFlow.update { DataState.Loading }

        viewModelScope.launch {
            runCatching {
                geminiService.searchRecipes(query)
            }
                .onSuccess { searchResult ->
                    val favIds = getFavoriteRecipeIds()
                    val newRecipes = searchResult.map { recipe ->
                        RecipeItemViewModel(
                            id = recipe.id,
                            title = recipe.title,
                            duration = recipe.durationMinutes,
                            favoriteState = recipe.id in favIds,
                            imageUrl = recipe.imageUrl,
                            ingredients = recipe.ingredients,
                            instructions = recipe.instructions,
                        )
                    }

                    recipesFlow.update { recipes ->
                        when (recipes) {
                            is DataState.Success -> {
                                val recipeInfo = recipes.data
                                DataState.Success(
                                    recipeInfo.copy(
                                        searchedRecipes = newRecipes,
                                        displayedRecipes = newRecipes,
                                        isDisplayingSearchResults = true
                                    )
                                )
                            }

                            else -> {
                                DataState.Success(
                                    RecipeInfo(
                                        searchedRecipes = newRecipes,
                                        favoriteRecipes = emptyList(),
                                        displayedRecipes = newRecipes,
                                        isDisplayingSearchResults = true
                                    )
                                )
                            }
                        }
                    }
                }
                .onFailure { e ->
                    recipesFlow.update { DataState.Failed(Throwable(e)) }
                }
        }
    }


    fun onFavoriteClick(clickedRecipe: RecipeItemViewModel) {
        viewModelScope.launch {
            appDataStore.updateData { current ->
                val recipeIsFavored = current.recipes.any { it.id == clickedRecipe.id }

                val favoriteRecipes = if (recipeIsFavored) {
                    current.recipes.filterNot { it.id == clickedRecipe.id }
                } else {
                    current.recipes + clickedRecipe.copy(favoriteState = true)
                }

                current.copy(recipes = favoriteRecipes)
            }

            loadFavoriteRecipes()

            updateFavoriteState(clickedRecipe)
        }
    }

    fun loadSelectedRecipe(recipeId: String) {
        selectedRecipeFlow.execute {
            val favoriteRecipes = appDataStore.data.first().recipes
            val allRecipes = if (recipesFlow.value is DataState.Uninitialized) {
                favoriteRecipes
            } else {
                favoriteRecipes + recipesFlow.value.data.searchedRecipes
            }

            val recipe = allRecipes.firstOrNull { it.id == recipeId }
                ?: error("Recipe not found: $recipeId")

            update(recipe)
        }
    }


    fun loadFavoriteRecipes() {
        viewModelScope.launch {
            runCatching {
                appDataStore.data.first()
            }.onSuccess { favoriteRecipes ->
                recipesFlow.update { recipes ->
                    when (recipes) {
                        is DataState.Success -> {
                            val recipeInfo = recipes.data
                            val shouldShowFavorites = !recipeInfo.isDisplayingSearchResults

                            DataState.Success(
                                recipeInfo.copy(
                                    favoriteRecipes = favoriteRecipes.recipes,
                                    displayedRecipes = if (shouldShowFavorites) {
                                        favoriteRecipes.recipes
                                    } else {
                                        recipeInfo.displayedRecipes
                                    }
                                )
                            )
                        }

                        else -> {
                            DataState.Success(
                                RecipeInfo(
                                    searchedRecipes = emptyList(),
                                    favoriteRecipes = favoriteRecipes.recipes,
                                    displayedRecipes = favoriteRecipes.recipes,
                                    isDisplayingSearchResults = false
                                )
                            )
                        }
                    }
                }
            }.onFailure {
                Log.e("DataStore", "Error loading favorite recipes: ${it.message}")
            }
        }
    }

    fun resetToFavorites() {
        recipesFlow.update { recipes ->
            val recipeInfo = recipes.dataOrNull ?: return@update recipes
            DataState.Success(
                recipeInfo.copy(
                    searchedRecipes = emptyList(),
                    displayedRecipes = recipeInfo.favoriteRecipes,
                    isDisplayingSearchResults = false
                )
            )
        }
    }

    private suspend fun getFavoriteRecipeIds(): Set<String> =
        appDataStore.data.first().recipes.map { it.id }.toSet()

    private fun updateFavoriteState(recipeItem: RecipeItemViewModel) {
        viewModelScope.launch {
            recipesFlow.update { recipes ->
                val recipeInfo = recipes.dataOrNull ?: return@update recipes

                val updatedFavorites = recipeInfo.favoriteRecipes.map {
                    if (it.id == recipeItem.id) it.copy(favoriteState = !it.favoriteState)
                    else it.copy(favoriteState = it.favoriteState)
                }

                val updatedDisplayed = recipeInfo.displayedRecipes.map {
                    if (it.id == recipeItem.id) it.copy(favoriteState = !it.favoriteState)
                    else it.copy(favoriteState = it.favoriteState)
                }

                DataState.Success(
                    recipeInfo.copy(
                        favoriteRecipes = updatedFavorites,
                        displayedRecipes = updatedDisplayed
                    )
                )
            }
        }
    }
}

@Stable
@Serializable
@Parcelize
data class RecipeItemViewModel(
    val id: String,
    val title: String,
    val duration: Int,
    val imageUrl: String,
    val favoriteState: Boolean,
    val ingredients: List<String>,
    val instructions: List<String>
) : Parcelable {

    @IgnoredOnParcel
    var isFavorite by mutableStateOf(favoriteState)
        private set

    fun toggleFavorite() {
        isFavorite = !isFavorite
    }
}
