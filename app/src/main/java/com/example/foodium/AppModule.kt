package com.example.foodium

import android.content.Context
import com.example.foodium.network.BackendApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class BackendApi(private val retrofit:Retrofit){
    val retrofitService : BackendApiService by lazy {
        retrofit.create(BackendApiService::class.java)
    }
}
private const val BASE_URL =
    "https://foodiumnodejs.gentledune-9460edf8.southafricanorth.azurecontainerapps.io"
class AppContainer(private val appContext: Context) {
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }
    val backendApi by lazy { BackendApi(retrofit) }
    val preferencesDataStore by lazy { FoodiumPreferencesStore(appContext) }
}
