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
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import javax.inject.Inject

val Context.dataStore: DataStore<FavoriteRecipes> by dataStore(
    fileName = "app-settings.json",
    serializer = FavoriteSerializer
)

//TODO see if you have time to implement this
data class RecipeInfo(
    val searchedRecipes: List<RecipeItemViewModel>,
    val favoriteRecipes: List<RecipeItemViewModel>,
    val displayedRecipes: List<RecipeItemViewModel>
)

@HiltViewModel
class RecipesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val geminiService: GeminiService,
) : BaseViewModel<Unit>() {
    val recipesFlow = dataFlowOf<List<RecipeItemViewModel>>()
    val favoriteRecipesFlow = dataFlowOf<List<RecipeItemViewModel>>()
    val selectedRecipeFlow = dataFlowOf<RecipeItemViewModel>()

    private val appDataStore: DataStore<FavoriteRecipes> = context.dataStore

    init {
        loadFavoriteRecipes()
    }

    fun searchRecipes(query: String) {
        recipesFlow.execute {
            val searchResult = geminiService.searchRecipes(query)

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

            update(newRecipes)
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
        if (selectedRecipeFlow.value is DataState.Uninitialized == false) {
            if (selectedRecipeFlow.value.data.id == recipeId) return
        }

        selectedRecipeFlow.execute {
            val allRecipes = if (recipesFlow.value is DataState.Uninitialized) {
                favoriteRecipesFlow.value.data
            } else {
                recipesFlow.value.data + favoriteRecipesFlow.value.data
            }
            val recipe = allRecipes.first { it.id == recipeId }
            update(recipe)
        }
    }

    private suspend fun updateFavoriteState(recipeItem: RecipeItemViewModel) {
        val favoriteIds = getFavoriteRecipeIds()
        recipesFlow.execute {
            update {
                it.data.map { recipe ->
                    if (recipe.id == recipeItem.id) {
                        recipe.copy(favoriteState = !recipe.favoriteState)
                    } else {
                        recipe.copy(favoriteState = recipe.id in favoriteIds)
                    }
                }
            }
        }
    }

    private fun loadFavoriteRecipes() {
        favoriteRecipesFlow.execute {
            runCatching {
                val favoriteRecipes = appDataStore.data.first()
                favoriteRecipes
            }
                .onSuccess {
                    update(it.recipes)
                }
                .onFailure {
                    Log.e("DataStore", "Error loading favorite recipes: ${it.message}")
                }
        }
    }

    private suspend fun getFavoriteRecipeIds(): Set<String> =
        appDataStore.data.first().recipes.map { it.id }.toSet()
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
