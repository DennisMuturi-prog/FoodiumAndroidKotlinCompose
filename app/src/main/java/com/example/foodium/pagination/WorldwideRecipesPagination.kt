package com.example.foodium.pagination

import android.util.Log
import com.example.foodium.network.BackendApi
import androidx.paging.PagingSource
import com.example.foodium.models.WorldwideRecipe
import androidx.paging.PagingState
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.RecipesRequest
import retrofit2.HttpException
import java.io.IOException

class WorldwideRecipesPagination(
    private val api: BackendApi,
    private val authTokens: AuthTokens
) : PagingSource<String, WorldwideRecipe>() {
    companion object {
        private const val STARTING_KEY = "first page"
    }

    override fun getRefreshKey(state: PagingState<String, WorldwideRecipe>): String? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.uuid
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, WorldwideRecipe> {
        val startKey = params.key ?: STARTING_KEY
        return try {
            val recipesResult = api.retrofitService.getWorldwideRecipes(RecipesRequest(region = "worldwide", numberOfResults = params.loadSize, next = startKey, accessToken =authTokens.accessToken, refreshToken = authTokens.refreshToken ))
            val previousKey=if(startKey== STARTING_KEY) null else recipesResult.previous
            val nextKey = recipesResult.next
            LoadResult.Page(
                data = recipesResult.results,
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