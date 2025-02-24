package com.example.foodium.network

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokens(val accessToken: String, val refreshToken: String)

@Serializable
data class RegisterData(val username: String, val email: String, val password: String)

@Serializable
data class LoginData(val username: String, val password: String)

@Serializable
data class UsernameData(val username: String, val accessToken: String, val refreshToken: String)

@Serializable
data class UserHealthAttributesData(
    val userWeight: Int,
    val userDietType: String,
    val userNumberOfMealsADay: Int,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class HealthAttributesData(
    val userWeight: Int,
    val userDietType: String,
    val userNumberOfMealsADay: Int
)

@Serializable
data class UsernameAddResponse(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val info: String,
    val serverStatus: Int,
    val warningStatus: Int,
    val changedRows: Int,
    val newTokens: AuthTokens? = null
)

@Serializable
data class MysqlAddFields(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val info: String,
    val serverStatus: Int,
    val warningStatus: Int,
    val changedRows: Int,
)

@Serializable
data class UserHealthAttributesAddResponse(
    val message: String,
    val result: MysqlAddFields,
    val newTokens: AuthTokens? = null
)

@Serializable
data class AuthenticatedUser(
    val id: String,
    val newTokens: AuthTokens? = null
)
@Serializable
data class RecipesRequest(
    val numberOfResults:Int,
    val region:String,
    val next:String,
    val accessToken: String,
    val refreshToken: String
)



