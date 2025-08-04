package com.example.recipefinder.ui.recipes

class RecipesViewModel {
}

data class RecipeItemViewModel(
    val id: String,
    val title: String,
    val duration: Int,
    val imageUrl: String,
    val isFavorite: Boolean,
)