package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Results(
    @SerialName("affectedRows")
    val affectedRows: Int,
    @SerialName("changedRows")
    val changedRows: Int,
    @SerialName("fieldCount")
    val fieldCount: Int,
    @SerialName("info")
    val info: String,
    @SerialName("insertId")
    val insertId: Int,
    @SerialName("serverStatus")
    val serverStatus: Int,
    @SerialName("warningStatus")
    val warningStatus: Int
)