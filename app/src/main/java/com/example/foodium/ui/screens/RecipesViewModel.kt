package com.example.foodium.ui.screens

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.repository.Repository
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.viewModelScope
import com.example.foodium.models.KenyanRecipe

class RecipesViewModel(private val repository: Repository) :ViewModel(){
    val recipes:Flow <PagingData<WorldwideRecipe>> = repository.getWorldwideRecipes().cachedIn(viewModelScope)
    val kenyanRecipes:Flow <PagingData<KenyanRecipe>> =repository.getKenyanRecipes().cachedIn(viewModelScope)

}