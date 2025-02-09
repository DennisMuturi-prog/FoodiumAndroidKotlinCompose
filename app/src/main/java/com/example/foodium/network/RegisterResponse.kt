package com.example.foodium.network
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse (val refreshToken:String, val accessToken:String)

@Serializable
data class RegisterData(val username:String,val email:String,val password:String)

@Serializable
data class LoginData(val username:String,val password:String)

@Serializable
data class UsernameData(val username:String,val accessToken: String,val refreshToken: String)

@Serializable
data class UsernameAddResponse(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val info: String,
    val serverStatus: Int,
    val warningStatus: Int,
    val changedRows: Int,
    val newTokens: RegisterResponse? = null
)

