package com.example.foodium.network

import kotlinx.serialization.Serializable

@Serializable
data class AuthTokens(val accessToken: String, val refreshToken: String)

@Serializable
data class RegisterData(val username: String, val email: String, val password: String)

@Serializable
data class LoginData(val username: String, val password: String)

@Serializable
data class UsernameData(val username: String, val accessToken: String, val refreshToken: String)

@Serializable
data class UserHealthAttributesData(
    val userWeight: Int,
    val userDietType: String,
    val userNumberOfMealsADay: Int,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class HealthAttributesData(
    val userWeight: Int,
    val userDietType: String,
    val userNumberOfMealsADay: Int
)

@Serializable
data class UsernameAddResponse(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val info: String,
    val serverStatus: Int,
    val warningStatus: Int,
    val changedRows: Int,
    val newTokens: AuthTokens? = null
)

@Serializable
data class MysqlAddFields(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val info: String,
    val serverStatus: Int,
    val warningStatus: Int,
    val changedRows: Int,
)

@Serializable
data class UserHealthAttributesAddResponse(
    val message: String,
    val result: MysqlAddFields,
    val newTokens: AuthTokens? = null
)

@Serializable
data class AuthenticatedUser(
    val id: String,
    val newTokens: AuthTokens? = null
)
@Serializable
data class RecipesRequest(
    val numberOfResults:Int,
    val region:String,
    val next:String,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class ForYouRecipesRequest(
    val numberOfResults:Int,
    val region:String,
    val next:String,
    val noOfMeals:Int,
    val userWeight: Int,
    val dietType:String,
    val accessToken: String,
    val refreshToken: String
)


@Serializable
data class AddReview(
    val reviewText:String,
    val recipeId:String,
    val region:String,
    val accessToken: String,
    val refreshToken: String
)
@Serializable
data class AddRating(
    val ratingNumber:Int,
    val recipeId:String,
    val region:String,
    val accessToken: String,
    val refreshToken: String
)
@Serializable
data class AddReviewResponse(
    val task:String,
    val newTokens:AuthTokens?=null
)
@Serializable
data class Search(
    val searchTerm:String,
    val region:String,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class SearchFood(
    val searchTerm:String,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class RecipeReviewsFetch(
    val region:String,
    val next:String,
    val recipeId:String,
    val numberOfResults: Int,
    val accessToken:String,
    val refreshToken: String
)

@Serializable
data class RecipeIntakeAdd(
    val region:String,
    val recipeId:String,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class FoodIntakeAdd(
    val foodId:String,
    val accessToken: String,
    val refreshToken: String
)
@Serializable
data class OnAddIntakeDetails(
    val recipeId: String,
    val region: String
)

@Serializable
data class UserRecipeIntakeRequest(
    val region: String,
    val next:String,
    val accessToken: String,
    val refreshToken: String
)

@Serializable
data class UserRecipeIntakeRequestByDate(
    val region: String,
    val date1:String,
    val date2:String,
    val accessToken: String,
    val refreshToken: String
)

