package com.example.recipefinder.ui.recipes

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.recipefinder.data.services.GeminiService2
import com.example.recipefinder.ui.base.BaseViewModel
import com.example.recipefinder.utils.dataFlowOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val geminiService: GeminiService2,
) : BaseViewModel<Unit>() {

    val recipesFlow = dataFlowOf<List<RecipeItemViewModel>>()
    private val searchQueryFlow = MutableStateFlow("")

    init {
        loadFavoriteRecipes()
    }

    fun onFavoriteClick(recipeId: String) {
        /* TODO */
    }

    private fun loadFavoriteRecipes() {
        recipesFlow.execute {
            update(mockedRecipes)
        }
    }

    fun searchRecipes(query: String) {
        recipesFlow.execute {
            val searchResult = geminiService.searchRecipes(query)

            Log.d("geminiVM", searchResult.toString())

            val newRecipes = searchResult.map { recipe ->
                RecipeItemViewModel(
                    id = recipe.id,
                    title = recipe.title,
                    duration = recipe.durationMinutes,
                    favoriteState = false,
                    imageUrl = recipe.imageUrl,
                    ingredients = recipe.ingredients,
                    instructions = recipe.instructions,
                )
            }

            update(newRecipes)
        }
    }
}

@Stable
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
        title = "Tasty Burger",
        duration = 20,
        favoriteState = true,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ingredients = listOf(),
        instructions = listOf()

    ),
    RecipeItemViewModel(
        id = "2",
        title = "Delicious Pasta",
        duration = 20,
        favoriteState = true,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ingredients = listOf(),
        instructions = listOf()
    ),
    RecipeItemViewModel(
        id = "3",
        title = "Fish & Chips",
        duration = 10,
        favoriteState = true,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        ingredients = listOf(),
        instructions = listOf()
    )
)