package com.example.foodium.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.foodium.serverSentEvents.NewReviewsViewModel

@Composable
fun NewReviews(modifier: Modifier = Modifier, newReviewsViewModel: NewReviewsViewModel) {
    val newReviewsState = newReviewsViewModel.sseEvents.observeAsState()
//    LaunchedEffect(Unit) {
//        Log.d("launched foof","done")
//        newReviewsViewModel.getSSEEvents()
//    }
    if (newReviewsState.value != null) {
        val newReviews = newReviewsState.value
        if (newReviews != null) {
            newReviews.forEach { review ->
                review.reviewText?.let { Text(it) }
                review.reviewerName?.let { Text(it) }
            }
        }

    }
}