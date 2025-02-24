package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("nutriments")
    val nutriments: Nutriments?,
    @SerialName("nutrition_grades")
    val nutritionGrades: String,
    @SerialName("product_name")
    val productName: String
)