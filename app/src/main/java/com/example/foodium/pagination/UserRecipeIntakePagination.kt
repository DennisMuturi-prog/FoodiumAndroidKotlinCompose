package com.example.foodium.pagination

import android.util.Log
import com.example.foodium.network.BackendApi
import androidx.paging.PagingSource
import com.example.foodium.models.WorldwideRecipe
import androidx.paging.PagingState
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserRecipeIntake
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.RecipesRequest
import com.example.foodium.network.UserRecipeIntakeRequest
import retrofit2.HttpException
import java.io.IOException

class WorldwideRecipesIntakePagination(
    private val api: BackendApi,
    private val authTokens: AuthTokens,
    private val updateAuthTokens:(AuthTokens)->Unit
) : PagingSource<String, UserIntake>() {
    companion object {
        private const val STARTING_KEY = "first page"
    }

    override fun getRefreshKey(state: PagingState<String, UserIntake>): String? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.uuid
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, UserIntake> {
        val startKey = params.key ?: STARTING_KEY
        return try {
            val recipesIntakeResult = api.retrofitService.fetchUserRecipeIntake(UserRecipeIntakeRequest(region = "worldwide", next = startKey, accessToken =authTokens.accessToken, refreshToken = authTokens.refreshToken ))
            val previousKey=if(startKey== STARTING_KEY) null else recipesIntakeResult.previous
            val nextKey = recipesIntakeResult.next
            if(recipesIntakeResult.newTokens!=null){
                updateAuthTokens(recipesIntakeResult.newTokens)
            }
            LoadResult.Page(
                data = recipesIntakeResult.results,
                prevKey = previousKey,
                nextKey = nextKey
            )


        } catch (e: IOException) {
            Log.d("error page",e.toString())
            return LoadResult.Error(e)
        } catch (httpException: HttpException) {
            val errorMessage = httpException.response()?.errorBody()?.string() ?: "Unknown error"
            Log.d("error page",errorMessage)
            return LoadResult.Error(httpException)
        }
    }
}