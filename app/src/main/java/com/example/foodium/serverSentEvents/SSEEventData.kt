package com.example.foodium.serverSentEvents

import java.sql.Date

data class SSEEventData(
    val status: STATUS? = null,
    val task:String?=null,
    val reviewerName: String? = null,
    val reviewText:String?=null,
    val recipeId:String?=null,
    val createdAt: String?=null
)

data class SSEEventResponse(
    val task:String?=null,
    val reviewerName: String? = null,
    val reviewText:String?=null,
    val recipeId:String?=null,
    val createdAt:String?=null
)

data class NewReviews(
    val reviews: List<SSEEventData>
)

enum class STATUS {
    SUCCESS,
    ERROR,
    NONE,
    CLOSED,
    OPEN
}