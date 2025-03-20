package com.example.foodium.models


import com.example.foodium.network.AuthTokens
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserKenyanRecipeIntake(
    @SerialName("newTokens")
    val newTokens: AuthTokens?=null,
    @SerialName("next")
    val next: String,
    @SerialName("previous")
    val previous: String,
    @SerialName("results")
    val results: List<UserKenyanIntake>
)

@Serializable
data class UserKenyanRecipeIntakeByDate(
    @SerialName("newTokens")
    val newTokens: AuthTokens?=null,
    @SerialName("results")
    val results: List<UserKenyanIntake>
)
