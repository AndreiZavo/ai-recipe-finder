package com.example.recipefinder.ui.recipe

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetailsDestination(
    val title: String,
    val duration: Int,
    val imageUrl: String,
    val ingredients: List<String>,
    val instructions: List<String>,
)