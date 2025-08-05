package com.example.recipefinder.data.remote

import com.example.recipefinder.BuildConfig
import com.example.recipefinder.data.models.Recipe
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.serialization.json.Json

object GeminiService {
    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val jsonParser = Json { ignoreUnknownKeys = true }

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
                    "instructions": "Step by step instructions",
                    "imageUrl": "https://example.com/image.jpg"
                }
            ]
        """.trimIndent()

        val response = model.generateContent(prompt)
        val jsonText = response.text ?: "[]"

        val cleanJson = jsonText.substringAfter("[").substringBeforeLast("]").let { "[$it]" }

        return try {
            jsonParser.decodeFromString(cleanJson)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}