package com.example.foodium.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.repository.Repository
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.viewModelScope
import com.example.foodium.models.KenyanRecipe
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import retrofit2.HttpException


class RecipesViewModel(private val repository: Repository) : ViewModel() {
    var recipes: Flow<PagingData<WorldwideRecipe>> =
        repository.getWorldwideRecipes().cachedIn(viewModelScope)
    var kenyanRecipes: Flow<PagingData<KenyanRecipe>> =
        repository.getKenyanRecipes().cachedIn(viewModelScope)

    init {
        getTokens()
    }

    private fun attachDataSource() {
        recipes = repository.getWorldwideRecipes().cachedIn(viewModelScope)
        kenyanRecipes = repository.getKenyanRecipes().cachedIn(viewModelScope)
    }

    private fun getTokens() {
        viewModelScope.launch {
            try {
                repository.getAuthTokens()
                attachDataSource()

            } catch (e: HttpException) {
                Log.d("error in recipes", e.toString())
            }
        }

    }

}