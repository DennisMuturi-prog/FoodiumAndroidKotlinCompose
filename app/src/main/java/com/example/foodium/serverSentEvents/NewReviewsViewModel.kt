package com.example.foodium.serverSentEvents

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewReviewsViewModel(private val sseRepository:SSERepository): ViewModel() {

    private val _sseEvents = MutableLiveData<List<SSEEventData>>()
    val sseEvents:LiveData<List<SSEEventData>> = _sseEvents

    init{
        getSSEEvents()
    }

    fun getSSEEvents() = viewModelScope.launch {
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