package com.example.foodium

import android.content.Context
import com.example.foodium.network.BackendApi
import com.example.foodium.network.OpenFoodFactsApi
import com.example.foodium.repository.Repository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


private const val BASE_URL =
    "https://nodejsbackend-g0f5g7ddh0eqekff.southafricanorth-01.azurewebsites.net"
private const val OPEN_FOOD_FACTS_BASE_URL=
    "https://world.openfoodfacts.net"
class AppContainer(private val appContext: Context) {
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }
    private val openFoodFactsRetrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(OPEN_FOOD_FACTS_BASE_URL)
            .build()
    }
    private val openFoodFactsAPi by lazy {OpenFoodFactsApi(openFoodFactsRetrofit)}
    private val backendApi by lazy { BackendApi(retrofit) }
    private val preferencesDataStore by lazy { FoodiumPreferencesStore(appContext) }
    val repository by lazy {Repository(backendApi=backendApi,preferencesDataStore=preferencesDataStore,openFoodFactsApi=openFoodFactsAPi)
    }
}
