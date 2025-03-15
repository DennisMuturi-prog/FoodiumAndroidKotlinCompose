package com.example.foodium.ui.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.serverSentEvents.NewReviewsViewModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period
import java.time.ZonedDateTime


@Composable
fun NewReviews(modifier: Modifier = Modifier, newReviewsViewModel: NewReviewsViewModel,recipeId:String) {
    val newReviewsState = newReviewsViewModel.sseEvents.observeAsState()
    LaunchedEffect(Unit) {
        newReviewsViewModel.changeToCurrentRecipeReviews(recipeId)
    }
    val recipeReviews=newReviewsViewModel.reviews.collectAsLazyPagingItems()
    if (newReviewsState.value != null) {
        val newReviews = newReviewsState.value
        if (newReviews != null) {
            newReviews.forEach { review ->
                review.reviewText?.let { Text(it) }
                review.reviewerName?.let { Text(it) }
            }
        }

    }
    LazyColumn(
        modifier = Modifier.height(500.dp)
    ) {
        items(recipeReviews.itemCount){index->
            recipeReviews[index]?.let {review->
                YoutubeStyleReviewItem(
                    reviewerName = review.username,
                    reviewText = review.reviewText,
                    createdAt = convertDateToAFormat(review.createdAt)
                )
            }
        }

    }


}

fun convertDateToAFormat(dateParam:String):String{
    val dateTime=ZonedDateTime.parse(dateParam)
    val dateNow=ZonedDateTime.now()
    val period= Duration.between(dateTime,dateNow)
    if(period.toSeconds()<60){
        return "${period.toSeconds()} seconds ago"
    }
    else if(period.toMinutes()<60){
        return "${period.toMinutes()} minutes ago"
    }
    else if(period.toHours()<24){
        return "${period.toHours()} hours ago"
    }
    else if(period.toDays()<30){
        return "${period.toDays()} days ago"
    }
    else{
        val months=period.toDays()/30
        return "$months months ago"

    }
}
