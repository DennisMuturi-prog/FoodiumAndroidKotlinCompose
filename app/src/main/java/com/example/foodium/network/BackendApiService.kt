package com.example.foodium.network

import com.example.foodium.models.AddRecipeIntakeResponse
import com.example.foodium.models.KenyanRecipeSearchResults
import com.example.foodium.models.KenyanRecipes
import com.example.foodium.models.OpenFoodFactsData
import com.example.foodium.models.Reviews
import com.example.foodium.models.UserKenyanRecipeIntake
import com.example.foodium.models.UserRecipeIntake
import com.example.foodium.models.WorldwideRecipeSearchResults
import com.example.foodium.models.WorldwideRecipes
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val queryFields="product_name,nutriments,nutrition_grades"

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

    @POST("addReview")
    suspend fun addReview(@Body review:AddReview):AddReviewResponse

    @POST("addRating")
    suspend fun  addRating(@Body rating:AddRating):AddReviewResponse

    @POST("searchRecipes")
    suspend fun searchKenyanRecipes(@Body search:Search):KenyanRecipeSearchResults

    @POST("searchRecipes")
    suspend fun searchWorldwideRecipes(@Body search:Search):WorldwideRecipeSearchResults

    @POST("getRecipeReviews")
    suspend fun getRecipeReviews(@Body recipe:RecipeReviewsFetch):Reviews

    @POST("addRecipeIntake")
    suspend fun addRecipeIntake(@Body recipeIntake:RecipeIntakeAdd):AddRecipeIntakeResponse

    @POST("getUserRecipeIntake")
    suspend fun fetchUserRecipeIntake(@Body userRecipeIntake:UserRecipeIntakeRequest):UserRecipeIntake

    @POST("getUserRecipeIntake")
    suspend fun fetchUserKenyanRecipeIntake(@Body userRecipeIntake:UserRecipeIntakeRequest):UserKenyanRecipeIntake


}
interface OpenFoodFactsService {
    @GET("api/v2/product/{barCodeString}")
    suspend fun getFoodInfo(@Path("barCodeString") barCodeString:String,@Query("fields") fields:String= queryFields):OpenFoodFactsData

}

class OpenFoodFactsApi(private val retrofit: Retrofit) {
    val openFoodFactsService:OpenFoodFactsService by lazy{
        retrofit.create(OpenFoodFactsService::class.java)
    }
}

class BackendApi(private val retrofit: Retrofit) {
    val retrofitService: BackendApiService by lazy {
        retrofit.create(BackendApiService::class.java)
    }
}
