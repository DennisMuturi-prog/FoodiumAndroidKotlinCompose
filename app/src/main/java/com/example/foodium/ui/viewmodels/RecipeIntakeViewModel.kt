package com.example.foodium.ui.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.FoodIntake
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake
import com.example.foodium.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RecipeIntakeViewModel(private val repository: Repository):ViewModel() {
    private var errorRate= mutableIntStateOf(0)
    var recipeIntake: Flow<PagingData<UserIntake>> =repository.getRecipeIntake().cachedIn(viewModelScope)
    var kenyanRecipeIntake:Flow<PagingData<UserKenyanIntake>> = repository.getKenyanRecipeIntake().cachedIn(viewModelScope)
    var foodIntake:Flow<PagingData<FoodIntake>> = repository.getFoodIntake().cachedIn(viewModelScope)
    fun attachDataSource(){
        recipeIntake=repository.getRecipeIntake().cachedIn(viewModelScope)
        kenyanRecipeIntake=repository.getKenyanRecipeIntake().cachedIn(viewModelScope)
        foodIntake=repository.getFoodIntake().cachedIn(viewModelScope)
    }
    private val _recipeIntakeByDateState= MutableLiveData<RecipeIntakeByDateState>()
    val recipeIntakeByDate:LiveData<RecipeIntakeByDateState> = _recipeIntakeByDateState

    fun fetchRecipesByDate(date1:String,date2:String){
        _recipeIntakeByDateState.value=RecipeIntakeByDateState.Loading
        viewModelScope.launch {
            _recipeIntakeByDateState.value=try {
                val result=repository.getUserRecipeIntakeByDate(date1,date2)
                val kenyanResult=repository.getUserKenyanRecipeIntakeByDate(date1,date2)
                RecipeIntakeByDateState.Success(results = result, kenyanResults = emptyList())

            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                RecipeIntakeByDateState.Error(errorMessage)
            }catch (e:IOException){
                RecipeIntakeByDateState.Error(e.toString())
            }
            _recipeIntakeByDateState.value=try {
                val kenyanResult=repository.getUserKenyanRecipeIntakeByDate(date1,date2)
                when(val result=_recipeIntakeByDateState.value){
                    is RecipeIntakeByDateState.Success->{
                        RecipeIntakeByDateState.Success(results = result.results, kenyanResults = kenyanResult)
                    }
                    is RecipeIntakeByDateState.Error->{
                        RecipeIntakeByDateState.Success(results = emptyList(), kenyanResults = kenyanResult)
                    }
                    is RecipeIntakeByDateState.Loading->{RecipeIntakeByDateState.Success(results = emptyList(), kenyanResults = kenyanResult)}
                    null->{RecipeIntakeByDateState.Success(results = emptyList(), kenyanResults = kenyanResult)
                    }
                }

            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                when(val result=_recipeIntakeByDateState.value){
                    is RecipeIntakeByDateState.Success->{
                        RecipeIntakeByDateState.Success(results = result.results, kenyanResults = emptyList())
                    }
                    is RecipeIntakeByDateState.Error->{
                        RecipeIntakeByDateState.Error(errorMessage)
                    }
                    is RecipeIntakeByDateState.Loading->{RecipeIntakeByDateState.Error(errorMessage)}
                    null->{RecipeIntakeByDateState.Error(errorMessage)
                    }
                }

                RecipeIntakeByDateState.Error(errorMessage)
            }catch (e:IOException){
                RecipeIntakeByDateState.Error(e.toString())
            }

        }

    }


}