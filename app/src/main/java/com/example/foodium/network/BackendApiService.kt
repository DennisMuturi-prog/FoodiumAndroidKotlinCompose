package com.example.foodium.network

import com.example.foodium.models.KenyanRecipes
import com.example.foodium.models.WorldwideRecipes
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApiService {
    @POST("register")
    suspend fun registerUser(@Body userData: RegisterData): AuthTokens

    @POST("login")
    suspend fun loginUser(@Body userData: LoginData): AuthTokens

    @POST("addUsername")
    suspend fun addUsername(@Body username: UsernameData): UsernameAddResponse

    @POST("protected")
    suspend fun getAuthTokens(@Body userTokens: AuthTokens): AuthenticatedUser

    @POST("addUserPreference")
    suspend fun addUserHealthAttributes(@Body userHealthData: UserHealthAttributesData): UserHealthAttributesAddResponse

    @POST("getRecipes")
    suspend fun getKenyanRecipes(@Body recipesRequest: RecipesRequest):KenyanRecipes

    @POST("getRecipes")
    suspend fun getWorldwideRecipes(@Body recipesRequest: RecipesRequest):WorldwideRecipes



}

class BackendApi(private val retrofit: Retrofit) {
    val retrofitService: BackendApiService by lazy {
        retrofit.create(BackendApiService::class.java)
    }
}
