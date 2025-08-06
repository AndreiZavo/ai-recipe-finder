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


data class RecipesInfo(
    val searchedRecipes: List<RecipeItemViewModel>,
    val favoriteRecipes: List<RecipeItemViewModel>
)


val Context.dataStore: DataStore<FavoriteRecipes> by dataStore(
    fileName = "app-settings.json",
    serializer = FavoriteSerializer
)

@HiltViewModel
class RecipesViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val geminiService: GeminiService,
) : BaseViewModel<Unit>() {

    private val appDataStore: DataStore<FavoriteRecipes> = context.dataStore

    val recipesFlow = dataFlowOf<List<RecipeItemViewModel>>()
    val favoriteRecipesFlow = dataFlowOf<List<RecipeItemViewModel>>()

    init {
        loadFavoriteRecipes()
    }

    fun searchRecipes(query: String) {
        recipesFlow.execute {
            val searchResult = geminiService.searchRecipes(query)

            Log.d("geminiVM", searchResult.toString())

            val favIds = favoriteIds()
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

            val favIds = favoriteIds()
            recipesFlow.execute {
                update {
                    it.data.map { recipe ->
                        if (recipe.id == clickedRecipe.id) {
                            recipe.copy(favoriteState = !recipe.favoriteState)
                        } else {
                            recipe.copy(favoriteState = recipe.id in favIds)
                        }
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
                    Log.d("DataStore", "Favorite recipes loaded: ${it.recipes}")
                    update(it.recipes)
                }
                .onFailure {
                    Log.e("DataStore", "Error loading favorite recipes: ${it.message}")
                }
        }
    }

    private suspend fun favoriteIds(): Set<String> =
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


val mockedRecipes = listOf<RecipeItemViewModel>(
    RecipeItemViewModel(
        id = "1",
        title = "Mocked Tasty Burger",
        duration = 20,
        favoriteState = true,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ingredients = listOf(),
        instructions = listOf()

    ),
    RecipeItemViewModel(
        id = "2",
        title = "Mocked Delicious Pasta",
        duration = 20,
        favoriteState = true,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ingredients = listOf(),
        instructions = listOf()
    ),
    RecipeItemViewModel(
        id = "3",
        title = "Mocked Fish & Chips",
        duration = 10,
        favoriteState = true,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ingredients = listOf(),
        instructions = listOf()
    )
)