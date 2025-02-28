package com.example.foodium.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun AddReview(modifier: Modifier = Modifier,onSend:(String)->Unit) {
    var review by remember {mutableStateOf("")}
    TextField(
        value = review,
        onValueChange = {review=it},
        placeholder = {Text("write a review")}
    )
    Button(
        onClick = {
            onSend(review)
        }
    ) {
       Icon(imageVector = Icons.AutoMirrored.Filled.Send,
           contentDescription = "send"
           )

    }

}