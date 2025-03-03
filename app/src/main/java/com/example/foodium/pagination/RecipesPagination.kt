package com.example.foodium.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.BackendApi
import com.example.foodium.network.RecipesRequest
import retrofit2.HttpException
import java.io.IOException

class RecipesPagination(
    private val api: BackendApi,
    private val authTokens: AuthTokens
) : PagingSource<String, KenyanRecipe>() {
    companion object {
        private const val STARTING_KEY = "first page"
    }

    override fun getRefreshKey(state: PagingState<String, KenyanRecipe>): String? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.uuid
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, KenyanRecipe> {
        val startKey = params.key ?: STARTING_KEY
        return try {
            val recipesResult = api.retrofitService.getKenyanRecipes(RecipesRequest(region = "kenyan", numberOfResults = 10, next = startKey, accessToken =authTokens.accessToken, refreshToken = authTokens.refreshToken ))
            val previousKey=if(startKey== STARTING_KEY) null else recipesResult.previous
            val nextKey = recipesResult.next
            LoadResult.Page(
                data = recipesResult.results,
                prevKey = previousKey,
                nextKey = nextKey
            )


        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (httpException: HttpException) {
            return LoadResult.Error(httpException)
        }
    }
}