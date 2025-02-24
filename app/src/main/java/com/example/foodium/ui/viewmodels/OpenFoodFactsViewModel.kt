package com.example.foodium.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.models.OpenFoodFactsData
import com.example.foodium.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface FoodInfoState {
    data class Success(val foodFacts: OpenFoodFactsData) : FoodInfoState
    data class Error(val message: String) : FoodInfoState
    data object Loading : FoodInfoState
}

class OpenFoodFactsViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _foodInfoState = MutableLiveData<FoodInfoState>()
    val foodInfoState : LiveData<FoodInfoState> = _foodInfoState
    fun getFoodInfo(barcode: String) {
        _foodInfoState.value=FoodInfoState.Loading
        viewModelScope.launch {
            _foodInfoState.value = try {
                val result = repository.getFoodInfo(barcode=barcode)
                FoodInfoState.Success(result)
            }catch(e:IOException){
                FoodInfoState.Error(e.toString())

            }catch(e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                FoodInfoState.Error(errorMessage)
            }
        }
    }
}