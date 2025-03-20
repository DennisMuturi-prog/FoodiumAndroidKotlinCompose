package com.example.foodium.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake

sealed interface RecipeIntakeByDateState {
    data class Success(val results:List<UserIntake>,val kenyanResults:List<UserKenyanIntake>) : RecipeIntakeByDateState
    data class Error(val message: String) : RecipeIntakeByDateState
    data object Loading : RecipeIntakeByDateState
}