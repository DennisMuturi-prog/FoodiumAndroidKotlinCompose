package com.example.foodium.models


import com.example.foodium.network.AuthTokens
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorldwideRecipes(
    @SerialName("previous")
    val previous: String?=null,
    @SerialName("next")
    val next: String,
    @SerialName("results")
    val results: List<WorldwideRecipe>,

    @SerialName("newTokens")
    val newTokens: AuthTokens?=null
)