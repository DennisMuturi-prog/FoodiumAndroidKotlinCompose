package com.example.foodium.serverSentEvents

import java.util.Date

data class SSEEventData(
    val status: STATUS? = null,
    val reviewerName: String? = null,
    val reviewText:String?=null,
    val createdAt:Date?=null
)

enum class STATUS {
    SUCCESS,
    ERROR,
    NONE,
    CLOSED,
    OPEN
}