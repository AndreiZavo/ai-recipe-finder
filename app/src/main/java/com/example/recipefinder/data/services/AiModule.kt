package com.example.recipefinder.data.services

import com.example.recipefinder.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AiModule {

    @Provides
    @Singleton
    fun provideGeminiApiKey(): String = BuildConfig.GEMINI_API_KEY

    @Provides
    @Singleton
    @Named("geminiModelName")
    fun provideModelName(): String = "gemini-2.5-flash"

    @Provides
    @Singleton
    fun provideGenerativeModel(
        @Named("geminiModelName") modelName: String,
        apiKey: String
    ): GenerativeModel = GenerativeModel(modelName = modelName, apiKey = apiKey)
}