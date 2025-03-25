package com.example.foodium.models


import com.example.foodium.network.AuthTokens
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodSearchResults(
    @SerialName("results")
    val results: List<Food>,
    @SerialName("newTokens")
    val newTokens:AuthTokens?=null
)