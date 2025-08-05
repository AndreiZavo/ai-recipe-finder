package com.example.recipefinder.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.recipefinder.data.remote.GeminiService
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeFinderTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .navigationBarsPadding()
                ) {
//                    CompositionLocalProvider(
//                        LocalTextSelectionColors provides AppColors.TextSelectionColors
//                    ) {
//                        RootApp()
//                    }

                    LaunchedEffect(Unit) {
                        try {
                            val recipes = GeminiService.searchRecipes("chocolate cake")
                            Log.d("GeminiTest", "Recipes: $recipes")
                        } catch (e: Exception) {
                            Log.e("GeminiTest", "Error fetching recipes", e)
                        }
                    }

                }
            }
        }
    }
}

