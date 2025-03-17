package com.example.foodium

import android.content.Context
import com.example.foodium.network.BackendApi
import com.example.foodium.network.OpenFoodFactsApi
import com.example.foodium.repository.Repository
import com.example.foodium.serverSentEvents.SSERepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


private const val BASE_URL =
    "https://3050-102-219-210-254.ngrok-free.app"
private const val OPEN_FOOD_FACTS_BASE_URL=
    "https://world.openfoodfacts.net"
class AppContainer(private val appContext: Context) {
    private val json = Json {
        ignoreUnknownKeys = true // Ignores unexpected fields
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }
    private val openFoodFactsRetrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(OPEN_FOOD_FACTS_BASE_URL)
            .build()
    }
    private val openFoodFactsAPi by lazy {OpenFoodFactsApi(openFoodFactsRetrofit)}
    private val backendApi by lazy { BackendApi(retrofit) }
    val sseRepository by lazy {SSERepository()}
    private val preferencesDataStore by lazy { FoodiumPreferencesStore(appContext) }
    val repository by lazy {Repository(backendApi=backendApi,preferencesDataStore=preferencesDataStore,openFoodFactsApi=openFoodFactsAPi)
    }
}
