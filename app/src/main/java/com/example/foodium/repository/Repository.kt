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
import com.example.foodium.models.Food
import com.example.foodium.models.FoodIntake
import com.example.foodium.models.OpenFoodFactsData
import com.example.foodium.models.RecipeReview
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake
import com.example.foodium.models.UserRecipeIntake
import com.example.foodium.models.UserRecipeIntakeByDate
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.network.AddRating
import com.example.foodium.network.AddReview
import com.example.foodium.network.AddReviewResponse
import com.example.foodium.network.FoodIntakeAdd
import com.example.foodium.network.OpenFoodFactsApi
import com.example.foodium.network.RecipeIntakeAdd
import com.example.foodium.network.Search
import com.example.foodium.network.SearchFood
import com.example.foodium.network.UserRecipeIntakeRequestByDate
import com.example.foodium.pagination.FOrYouWorldwideRecipesPagination
import com.example.foodium.pagination.ForYouRecipesPagination
import com.example.foodium.pagination.KenyanRecipesIntakePagination
import com.example.foodium.pagination.ReviewsPagination
import com.example.foodium.pagination.UserFoodIntakeIntakePagination
import com.example.foodium.pagination.WorldwideRecipesIntakePagination
import com.example.foodium.pagination.WorldwideRecipesPagination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class Repository(
    private val backendApi: BackendApi,
    private val preferencesDataStore: FoodiumPreferencesStore,
    private val openFoodFactsApi: OpenFoodFactsApi

) {
    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private var authTokens = AuthTokens("", "")
    private var healthAttributes = HealthAttributesData(userWeight = 70, userNumberOfMealsADay = 3, userDietType = "none")
    suspend fun registerUser(userData: RegisterData) {
        val result = backendApi.retrofitService.registerUser(userData)
        authTokens = result
        preferencesDataStore.saveString("accessToken", result.accessToken)
        preferencesDataStore.saveString("refreshToken", result.refreshToken)
    }

    init {
        repositoryScope.launch {
            val accessToken = preferencesDataStore.getString("accessToken")
            val refreshToken = preferencesDataStore.getString("refreshToken")
            if (accessToken != null && refreshToken != null) {
                authTokens = AuthTokens(accessToken = accessToken, refreshToken = refreshToken)
            }

        }
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
        healthAttributes=healthData
    }

    suspend fun getAuthTokensFromServer(): String {
        val accessToken = preferencesDataStore.getString("accessToken")
        val refreshToken = preferencesDataStore.getString("refreshToken")
        val userWeight =preferencesDataStore.getString("userWeight")
        val userNoOfMeals=preferencesDataStore.getString("userNoOfMeals")
        val userDietType=preferencesDataStore.getString("userDietType")
        if(userWeight!=null && userNoOfMeals!=null && userDietType!=null){
            healthAttributes= HealthAttributesData(userDietType = userDietType, userWeight = userWeight.toInt(), userNumberOfMealsADay = userNoOfMeals.toInt())
        }
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

    suspend fun getAuthTokens() {
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
                authTokens = result.newTokens
            } else {
                authTokens = AuthTokens(accessToken, refreshToken)
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

    fun getKenyanRecipes(): Flow<PagingData<KenyanRecipe>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                RecipesPagination(backendApi, authTokens, updateAuthTokens = {
                    authTokens = it
                })
            }
        ).flow
    }

    fun getWorldwideRecipes(): Flow<PagingData<WorldwideRecipe>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                WorldwideRecipesPagination(backendApi, authTokens, updateAuthTokens = {
                    authTokens = it
                })
            }
        ).flow
    }
    fun getKenyanRecipesByDiet() :Flow<PagingData<KenyanRecipe>> {
        Log.d("user health",healthAttributes.userDietType)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                ForYouRecipesPagination(backendApi,authTokens, updateAuthTokens = {
                    authTokens=it
                },healthAttributes.userWeight,healthAttributes.userNumberOfMealsADay,healthAttributes.userDietType)
            }
        ).flow
    }
    fun getWorldwideRecipesByDiet() :Flow<PagingData<WorldwideRecipe>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                FOrYouWorldwideRecipesPagination(backendApi,authTokens, updateAuthTokens = {
                    authTokens=it
                },healthAttributes.userWeight,healthAttributes.userNumberOfMealsADay,healthAttributes.userDietType)
            }
        ).flow
    }


    fun getRecipeReviews(recipeId: String, region: String): Flow<PagingData<RecipeReview>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                ReviewsPagination(api = backendApi,
                    authTokens = authTokens,
                    recipeId = recipeId,
                    region = region,
                    updateAuthTokens = {
                        authTokens = it
                    })
            }
        ).flow
    }

    fun getRecipeIntake(): Flow<PagingData<UserIntake>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                WorldwideRecipesIntakePagination(
                    api = backendApi,
                    authTokens = authTokens,
                    updateAuthTokens = {
                        authTokens = it
                    })
            }
        ).flow
    }

    fun getKenyanRecipeIntake(): Flow<PagingData<UserKenyanIntake>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                KenyanRecipesIntakePagination(
                    api = backendApi,
                    authTokens = authTokens,
                    updateAuthTokens = {
                        authTokens = it
                    })
            }
        ).flow
    }
    fun getFoodIntake(): Flow<PagingData<FoodIntake>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                UserFoodIntakeIntakePagination(
                    api = backendApi,
                    authTokens = authTokens,
                    updateAuthTokens = {
                        authTokens = it
                    })
            }
        ).flow
    }

    suspend fun getFoodInfo(barcode: String): OpenFoodFactsData {
        return openFoodFactsApi.openFoodFactsService.getFoodInfo(barCodeString = barcode)
    }

    suspend fun addReview(reviewText: String, region: String, recipeId: String): AddReviewResponse {
        val result = backendApi.retrofitService.addReview(
            AddReview(
                reviewText = reviewText,
                region = region,
                recipeId = recipeId,
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken
            )
        )
        if (result.newTokens != null) {
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
        }
        return result
    }

    suspend fun addRating(ratingNumber: Int, region: String, recipeId: String): AddReviewResponse {
        val result = backendApi.retrofitService.addRating(
            AddRating(
                ratingNumber = ratingNumber,
                region = region,
                recipeId = recipeId,
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken
            )
        )
        if (result.newTokens != null) {
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
        }
        return result
    }

    suspend fun searchKenyanRecipes(searchTerm: String, region: String): Flow<List<KenyanRecipe>> {
        try {
            val result = backendApi.retrofitService.searchKenyanRecipes(
                Search(
                    searchTerm = searchTerm,
                    region = region,
                    accessToken = authTokens.accessToken,
                    refreshToken = authTokens.refreshToken
                )
            )
            if (result.newTokens != null) {
                authTokens = result.newTokens
                preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
                preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
            }
            return flowOf(result.results)


        }catch (e:Exception){
            return emptyFlow()

        }


    }
    suspend fun searchWorldwideRecipes(searchTerm: String, region: String): Flow<List<WorldwideRecipe>> {
        try {
            val result = backendApi.retrofitService.searchWorldwideRecipes(
                Search(
                    searchTerm = searchTerm,
                    region = region,
                    accessToken = authTokens.accessToken,
                    refreshToken = authTokens.refreshToken
                )
            )
            if (result.newTokens != null) {
                authTokens = result.newTokens
                preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
                preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
            }
            return flowOf(result.results)

        }catch (e:Exception){
            return  emptyFlow()

        }


    }
    suspend fun searchFoods(searchTerm: String): Flow<List<Food>> {
        try {
            val result = backendApi.retrofitService.searchFoods(
                SearchFood(
                    searchTerm = searchTerm,
                    accessToken = authTokens.accessToken,
                    refreshToken = authTokens.refreshToken
                )
            )
            if (result.newTokens != null) {
                authTokens = result.newTokens
                preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
                preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)
            }
            return flowOf(result.results)

        }catch (e:Exception){
            return  emptyFlow()
        }
    }

    suspend fun addRecipeIntake(recipeId: String, region: String) {
        val result = backendApi.retrofitService.addRecipeIntake(
            RecipeIntakeAdd(
                recipeId = recipeId,
                region = region,
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken
            )
        )
        if (result.newTokens != null) {
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)

        }
    }
    suspend fun addFoodIntake(foodId: String) {
        val result = backendApi.retrofitService.addFoodIntake(
            FoodIntakeAdd(
                foodId = foodId,
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken
            )
        )
        if (result.newTokens != null) {
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)

        }
    }

    suspend fun getUserRecipeIntakeByDate(date1: String, date2: String): List<UserIntake> {
        val result = backendApi.retrofitService.fetchUserRecipeIntakeByDate(
            UserRecipeIntakeRequestByDate(
                region = "worldwide",
                date1 = date1,
                date2 = date2,
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken
            )
        )
        if(result.newTokens!=null){
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)

        }
        return result.results

    }
    suspend fun getUserKenyanRecipeIntakeByDate(date1: String, date2: String): List<UserKenyanIntake> {
        val result = backendApi.retrofitService.fetchUserKenyanRecipeIntakeByDate(
            UserRecipeIntakeRequestByDate(
                region = "kenyan",
                date1 = date1,
                date2 = date2,
                accessToken = authTokens.accessToken,
                refreshToken = authTokens.refreshToken
            )
        )
        if(result.newTokens!=null){
            authTokens = result.newTokens
            preferencesDataStore.saveString("accessToken", result.newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken", result.newTokens.refreshToken)

        }
        return result.results

    }


}