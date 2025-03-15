package com.example.foodium.serverSentEvents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.RecipeReview
import com.example.foodium.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewReviewsViewModel(private val sseRepository:SSERepository,
    private val repository: Repository): ViewModel() {
    private val _sseEvents = MutableLiveData<List<SSEEventData>>()
    val sseEvents:LiveData<List<SSEEventData>> = _sseEvents
    var reviews: Flow<PagingData<RecipeReview>> =
        repository.getRecipeReviews("01948f0f-6846-77f7-99c8-479951831682").cachedIn(viewModelScope)

    init{
        getSSEEvents()
    }
    fun changeToCurrentRecipeReviews(recipeId:String){
        reviews=repository.getRecipeReviews(recipeId).cachedIn(viewModelScope)
    }

    private fun getSSEEvents() = viewModelScope.launch {
        sseRepository.sseEventsFlow
            .onEach { sseEventData ->
                Log.d("SSE trial",sseEventData.toString())
                _sseEvents.value=(_sseEvents.value?: emptyList())+sseEventData
                val myList=_sseEvents.value
                if (myList != null) {
                    Log.d("array size",myList.size.toString())
                }
            }
            .catch {
                Log.d("error hot","error hot")
                _sseEvents.value= (_sseEvents.value?: emptyList())+ SSEEventData(status = STATUS.ERROR)
            }
            .launchIn(viewModelScope)
    }

}