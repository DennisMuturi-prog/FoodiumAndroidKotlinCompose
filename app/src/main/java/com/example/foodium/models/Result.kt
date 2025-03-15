package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeReview(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("review_text")
    val reviewText: String,
    @SerialName("username")
    val username: String,
    @SerialName("uuid")
    val uuid: String
)