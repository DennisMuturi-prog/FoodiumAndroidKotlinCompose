package com.example.foodium.models


import com.example.foodium.network.AuthTokens
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRecipeIntakeResponse(
    @SerialName("newTokens")
    val newTokens: AuthTokens?=null,
    @SerialName("results")
    val results: Results
)