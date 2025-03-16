package com.example.foodium.pagination

import android.graphics.Region
import android.util.Log
import com.example.foodium.network.BackendApi
import androidx.paging.PagingSource
import com.example.foodium.models.WorldwideRecipe
import androidx.paging.PagingState
import com.example.foodium.models.RecipeReview
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.RecipeReviewsFetch
import com.example.foodium.network.RecipesRequest
import retrofit2.HttpException
import java.io.IOException

class ReviewsPagination(
    private val api: BackendApi,
    private val authTokens: AuthTokens,
    private val recipeId:String,
    private val region: String,
    private val updateAuthTokens:(AuthTokens)->Unit

) : PagingSource<String, RecipeReview>() {
    companion object {
        private const val STARTING_KEY = "first page"
    }

    override fun getRefreshKey(state: PagingState<String, RecipeReview>): String? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.uuid
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RecipeReview> {
        val startKey = params.key ?: STARTING_KEY
        return try {
            val recipeReviewsResult = api.retrofitService.getRecipeReviews(RecipeReviewsFetch(region = region, numberOfResults = params.loadSize, next = startKey, recipeId = recipeId, accessToken =authTokens.accessToken, refreshToken = authTokens.refreshToken ))
            val previousKey=if(startKey== STARTING_KEY) null else recipeReviewsResult.previous
            val nextKey = recipeReviewsResult.next
            if(recipeReviewsResult.newTokens!=null){
                updateAuthTokens(recipeReviewsResult.newTokens)
            }
            LoadResult.Page(
                data = recipeReviewsResult.results,
                prevKey = previousKey,
                nextKey = nextKey
            )


        } catch (e: IOException) {
            Log.d("error page reviews",e.toString())
            return LoadResult.Error(e)
        } catch (httpException: HttpException) {
            val errorMessage = httpException.response()?.errorBody()?.string() ?: "Unknown error"
            Log.d("error page",errorMessage)
            return LoadResult.Error(httpException)
        }
    }
}