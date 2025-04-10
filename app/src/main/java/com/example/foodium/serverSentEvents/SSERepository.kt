package com.example.foodium.serverSentEvents

import android.util.Log
import androidx.compose.ui.res.stringResource
import com.example.foodium.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources


class SSERepository() {
    private val sseClient = OkHttpClient.Builder()
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .build()


    private val sseRequest = Request.Builder()
        .url(EVENTS_URL)
        .header("Accept", "application/json")
        .addHeader("Accept", "text/event-stream")
        .build()

    var sseEventsFlow = MutableStateFlow(SSEEventData(STATUS.NONE))
        private set

    private val sseEventSourceListener = object : EventSourceListener() {
        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
            Log.d(TAG, "onClosed: $eventSource")
            val event = SSEEventData(STATUS.CLOSED)
            sseEventsFlow.tryEmit(event)
        }

        override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
            super.onEvent(eventSource, id, type, data)
            Log.d(TAG, "onEvent: $data")
            if (data.isNotEmpty()) {
                val reviewData = Gson().fromJson(data, SSEEventResponse::class.java)
                val event = SSEEventData(STATUS.SUCCESS, reviewText = reviewData.reviewText, reviewerName = reviewData.reviewerName,
                    createdAt = reviewData.createdAt, recipeId = reviewData.recipeId)
                sseEventsFlow.tryEmit(event)
            }
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            t?.printStackTrace()
            Log.d(TAG, "onFailure: ${t?.message}")
            val event = SSEEventData(STATUS.ERROR)
            sseEventsFlow.tryEmit(event)
        }

        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            Log.d(TAG, "onOpen: $eventSource")
            val event = SSEEventData(STATUS.OPEN)
            sseEventsFlow.tryEmit(event)
        }
    }

    init {
        Log.d("created sse","one")
        initEventSource()
    }

    private fun initEventSource() {
        EventSources.createFactory(sseClient)
            .newEventSource(request = sseRequest, listener = sseEventSourceListener)
    }

    companion object {
        private const val TAG = "SSERepository"
        private  const val EVENTS_URL = "https://3050-102-219-210-254.ngrok-free.app/reviewsEvent"
    }

}