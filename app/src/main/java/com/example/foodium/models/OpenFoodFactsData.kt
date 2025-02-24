package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenFoodFactsData(
    @SerialName("code")
    val code: String,
    @SerialName("product")
    val product: Product,
    @SerialName("status")
    val status: Int,
    @SerialName("status_verbose")
    val statusVerbose: String
)