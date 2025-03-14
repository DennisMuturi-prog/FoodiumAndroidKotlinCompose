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

class NewReviewsViewModel: ViewModel() {

    private val repository = SSERepository(refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwMTk1MjM2Zi05MGNmLTc4NzctYjQyMC04OWJkZjI5NzhmOTIiLCJyZWZyZXNoVG9rZW5WZXJzaW9uIjowLCJpYXQiOjE3NDE5NzQ1ODAsImV4cCI6MTc0NDU2NjU4MH0.Ckbpix5yItvG7GrFtXqA5XZkFSehtp9O6iNqcAnM6lo", accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIwMTk1MjM2Zi05MGNmLTc4NzctYjQyMC04OWJkZjI5NzhmOTIiLCJpYXQiOjE3NDE5NzQ1ODAsImV4cCI6MTc0MTk3ODE4MH0.qn6w3ENAg5Igwt1oTkdl2ViStyuo7imHdRq4ViY5Ahg", recipeId = "01948ec0-cc27-77ba-9a63-faa20a16afe4")

    private val _sseEvents = MutableLiveData<List<SSEEventData>>()
    val sseEvents:LiveData<List<SSEEventData>> = _sseEvents

    fun getSSEEvents() = viewModelScope.launch {
        repository.sseEventsFlow
            .onEach { sseEventData ->
                Log.d("SSE",sseEventData.toString())
                _sseEvents.value=(_sseEvents.value?: emptyList())+sseEventData
            }
            .catch {
                _sseEvents.value= (_sseEvents.value?: emptyList())+ SSEEventData(status = STATUS.ERROR)
            }
            .launchIn(viewModelScope)
    }

}