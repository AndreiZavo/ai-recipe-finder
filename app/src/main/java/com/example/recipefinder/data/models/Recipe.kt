package com.example.recipefinder.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val ingredients: List<String>,
    val instructions: String,
    val imageUrl: String? = null
)