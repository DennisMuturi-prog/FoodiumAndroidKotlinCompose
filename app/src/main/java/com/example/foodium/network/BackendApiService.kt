package com.example.foodium.network
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
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
    suspend fun registerUser(@Body userData:RegisterData): AuthTokens
    @POST("login")
    suspend fun loginUser(@Body userData:LoginData): AuthTokens
    @POST("addUsername")
    suspend fun addUsername(@Body username:UsernameData):UsernameAddResponse

    @POST("protected")
    suspend fun  getAuthTokens(@Body userTokens:AuthTokens):AuthenticatedUser
}

//object BackendApi {
//    val retrofitService : BackendApiService by lazy {
//        retrofit.create(BackendApiService::class.java)
//    }
//}
