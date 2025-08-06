package com.example.recipefinder.data.models

import com.example.recipefinder.ui.recipes.RecipeItemViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val ingredients: List<String>,
    val instructions: List<String>,
    val imageUrl: String
)

@Serializable
data class FavoriteRecipes(
    val recipes: List<RecipeItemViewModel> = persistentListOf()
)