package com.example.foodium.network
import retrofit2.Retrofit
import retrofit2.http.GET
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL =
    "https://foodiumnodejs.gentledune-9460edf8.southafricanorth.azurecontainerapps.io"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface BackendApiService {
    @POST("register")
    suspend fun registerUser(@Body userData:RegisterData): RegisterResponse
    @POST("login")
    suspend fun loginUser(@Body userData:LoginData): RegisterResponse
}

object BackendApi {
    val retrofitService : BackendApiService by lazy {
        retrofit.create(BackendApiService::class.java)
    }
}
