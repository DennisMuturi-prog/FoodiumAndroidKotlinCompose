package com.example.foodium.repository

import com.example.foodium.FoodiumPreferencesStore
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.BackendApi
import com.example.foodium.network.HealthAttributesData
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.network.UserHealthAttributesData
import com.example.foodium.network.UsernameData

class Repository(
    private val backendApi: BackendApi,
    private val preferencesDataStore: FoodiumPreferencesStore
) {
    private var authTokens = AuthTokens("", "")
    suspend fun registerUser(userData: RegisterData) {
        val result = backendApi.retrofitService.registerUser(userData)
        authTokens = result
        preferencesDataStore.saveString("accessToken", result.accessToken)
        preferencesDataStore.saveString("refreshToken", result.refreshToken)
    }

    suspend fun loginUser(userData: LoginData) {
        val result = backendApi.retrofitService.loginUser(userData)
        authTokens = result
        preferencesDataStore.saveString("accessToken", result.accessToken)
        preferencesDataStore.saveString("refreshToken", result.refreshToken)
    }

    suspend fun addUsername(username: String) {
        val result = backendApi.retrofitService.addUsername(
            UsernameData(
                username,
                authTokens.accessToken,
                authTokens.refreshToken
            )
        )
        if (result.newTokens != null) {
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
        }
    }

    suspend fun addUserHealthAttributes(healthData: HealthAttributesData) {
        val result = backendApi.retrofitService.addUserHealthAttributes(
            UserHealthAttributesData(
                healthData.userWeight,
                healthData.userDietType,
                healthData.userNumberOfMealsADay,
                authTokens.accessToken,
                authTokens.refreshToken
            )
        )
        if (result.newTokens != null) {
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
        }
        preferencesDataStore.saveString("userWeight", healthData.userWeight.toString())
        preferencesDataStore.saveString(
            "userNoOfMeals",
            healthData.userNumberOfMealsADay.toString()
        )
        preferencesDataStore.saveString("userDietType", healthData.userDietType)
    }

    suspend fun getAuthTokensFromServer(): String {
        val accessToken = preferencesDataStore.getString("accessToken")
        val refreshToken = preferencesDataStore.getString("refreshToken")
        if (accessToken == null || refreshToken == null) {
            return "no stored tokens found"
        } else {
            val result =
                backendApi.retrofitService.getAuthTokens(AuthTokens(accessToken, refreshToken))

            if (result.newTokens != null) {
                preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
                preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
                authTokens = result.newTokens
                return "authenticated"
            } else {
                return "authenticated"
            }
        }

    }

    suspend fun logout() {
        authTokens = AuthTokens("", "")
        preferencesDataStore.saveString("accessToken", "")
        preferencesDataStore.saveString("refreshToken", "")
    }

    suspend fun updatePreferencesDataStore(accessToken: String, refreshToken: String) {
        preferencesDataStore.saveString("accessToken", accessToken)
        preferencesDataStore.saveString("refreshToken", refreshToken)
        authTokens = AuthTokens(accessToken, refreshToken)
    }

}