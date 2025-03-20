package com.example.foodium.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake
import com.example.foodium.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RecipeIntakeViewModel(private val repository: Repository):ViewModel() {
    var recipeIntake: Flow<PagingData<UserIntake>> =repository.getRecipeIntake().cachedIn(viewModelScope)
    var kenyanRecipeIntake:Flow<PagingData<UserKenyanIntake>> = repository.getKenyanRecipeIntake().cachedIn(viewModelScope)
    fun attachDataSource(){
        recipeIntake=repository.getRecipeIntake().cachedIn(viewModelScope)
        kenyanRecipeIntake=repository.getKenyanRecipeIntake().cachedIn(viewModelScope)
    }
    private val _recipeIntakeByDateState= MutableLiveData<RecipeIntakeByDateState>()
    val recipeIntakeByDate:LiveData<RecipeIntakeByDateState> = _recipeIntakeByDateState

    fun fetchRecipesByDate(date1:String,date2:String){
        _recipeIntakeByDateState.value=RecipeIntakeByDateState.Loading
        viewModelScope.launch {
            _recipeIntakeByDateState.value=try {
                val result=repository.getUserRecipeIntakeByDate(date1,date2)
                val kenyanResult=repository.getUserKenyanRecipeIntakeByDate(date1,date2)
                RecipeIntakeByDateState.Success(results = result, kenyanResults = kenyanResult)

            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                RecipeIntakeByDateState.Error(errorMessage)
            }catch (e:IOException){
                RecipeIntakeByDateState.Error(e.toString())
            }

        }

    }


}