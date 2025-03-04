package com.example.foodium.models


import com.example.foodium.network.AuthTokens
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KenyanRecipeSearchResults(
    @SerialName("results")
    val results: List<KenyanRecipe>,
    val newTokens: AuthTokens?=null
)

@Serializable
data class WorldwideRecipeSearchResults(
    @SerialName("results")
    val results: List<WorldwideRecipe>,
    val newTokens: AuthTokens?=null
)
