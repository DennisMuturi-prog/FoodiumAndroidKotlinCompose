package com.example.foodium.network
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse (val refreshToken:String, val accessToken:String)

@Serializable
data class RegisterData(val username:String,val email:String,val password:String)

@Serializable
data class LoginData(val username:String,val password:String)