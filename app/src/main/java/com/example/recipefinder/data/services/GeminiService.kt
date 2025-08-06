package com.example.recipefinder.data.services

import android.util.Log
import com.example.recipefinder.data.models.Recipe
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GeminiService @Inject constructor(
    private val model: GenerativeModel
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun searchRecipes(query: String): List<Recipe> {
        val prompt = """
                Suggest 5 recipes based on: "$query".
                Return ONLY valid JSON in this exact format:
                [
                    {
                        "id": "unique_id",
                        "title": "Recipe title",
                        "durationMinutes": 20,
                        "ingredients": ["ingredient1", "ingredient2"],
                        "instructions": ["instruction1", "instruction2"],
                        "imageUrl": "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    }
                ]
                Rules:
                - Always use the same value for "imageUrl" exactly as shown above.
                - No other text before or after the JSON.
            """.trimIndent()

        val response = model.generateContent(prompt)
        val text = response.text ?: "[]"
        val clean = text.substringAfter("[").substringBeforeLast("]").let { "[$it]" }

        Log.d("geminiService", clean)

        return try {
            json.decodeFromString(clean)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}