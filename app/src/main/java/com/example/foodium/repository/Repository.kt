package com.example.foodium.repository

import android.util.Log
import com.example.foodium.FoodiumPreferencesStore
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.BackendApi
import com.example.foodium.network.HealthAttributesData
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.network.UserHealthAttributesData
import com.example.foodium.network.UsernameData
import com.example.foodium.pagination.RecipesPagination
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.foodium.models.OpenFoodFactsData
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.network.AddRating
import com.example.foodium.network.AddReview
import com.example.foodium.network.AddReviewResponse
import com.example.foodium.network.OpenFoodFactsApi
import com.example.foodium.pagination.WorldwideRecipesPagination
import kotlinx.coroutines.flow.Flow

class Repository(
    private val backendApi: BackendApi,
    private val preferencesDataStore: FoodiumPreferencesStore,
    private val openFoodFactsApi: OpenFoodFactsApi

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
    suspend fun getAuthTokens(){
        val accessToken = preferencesDataStore.getString("accessToken")
        val refreshToken = preferencesDataStore.getString("refreshToken")
        if (accessToken == null || refreshToken == null) {
            return
        } else {
            val result =
                backendApi.retrofitService.getAuthTokens(AuthTokens(accessToken, refreshToken))

            if (result.newTokens != null) {
                preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
                preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
                authTokens=result.newTokens
            } else {
                authTokens=AuthTokens(accessToken,refreshToken)
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
    fun getKenyanRecipes():Flow<PagingData<KenyanRecipe>>{
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { RecipesPagination(backendApi,authTokens) }
        ).flow
    }
    fun getWorldwideRecipes():Flow<PagingData<WorldwideRecipe>>{
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { WorldwideRecipesPagination(backendApi,authTokens) }
        ).flow
    }
    suspend fun getFoodInfo(barcode:String):OpenFoodFactsData{
        return openFoodFactsApi.openFoodFactsService.getFoodInfo(barCodeString = barcode)
    }
    suspend fun  addReview(reviewText:String,region:String,recipeId:String):AddReviewResponse{
        val result=backendApi.retrofitService.addReview(AddReview(reviewText=reviewText,region=region, recipeId = recipeId, accessToken = authTokens.accessToken, refreshToken = authTokens.refreshToken))
        if(result.newTokens!=null){
            authTokens=result.newTokens
            preferencesDataStore.saveString("accessToken",result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken",result.newTokens.refreshToken)
        }
        return result
    }
    suspend fun  addRating(ratingNumber:Int,region:String,recipeId:String):AddReviewResponse{
        val result=backendApi.retrofitService.addRating(AddRating(ratingNumber=ratingNumber,region=region, recipeId = recipeId, accessToken = authTokens.accessToken, refreshToken = authTokens.refreshToken))
        if(result.newTokens!=null){
            authTokens=result.newTokens
            preferencesDataStore.saveString("accessToken",result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken",result.newTokens.refreshToken)
        }
        return result
    }
}