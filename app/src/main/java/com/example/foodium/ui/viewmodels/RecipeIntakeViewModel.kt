package com.example.foodium.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake
import com.example.foodium.repository.Repository
import kotlinx.coroutines.flow.Flow

class RecipeIntakeViewModel(private val repository: Repository):ViewModel() {
    var recipeIntake: Flow<PagingData<UserIntake>> =repository.getRecipeIntake().cachedIn(viewModelScope)
    var kenyanRecipeIntake:Flow<PagingData<UserKenyanIntake>> = repository.getKenyanRecipeIntake().cachedIn(viewModelScope)
    fun attachDataSource(){
        recipeIntake=repository.getRecipeIntake().cachedIn(viewModelScope)
        kenyanRecipeIntake=repository.getKenyanRecipeIntake().cachedIn(viewModelScope)
    }
}