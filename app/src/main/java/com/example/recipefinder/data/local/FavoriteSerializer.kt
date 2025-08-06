package com.example.recipefinder.data.local

import androidx.datastore.core.Serializer
import com.example.recipefinder.data.models.FavoriteRecipes
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object FavoriteSerializer : Serializer<FavoriteRecipes> {
    override val defaultValue: FavoriteRecipes
        get() = FavoriteRecipes()

    override suspend fun readFrom(input: InputStream): FavoriteRecipes {
        return try {
            Json.decodeFromString(
                deserializer = FavoriteRecipes.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: FavoriteRecipes, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = FavoriteRecipes.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}