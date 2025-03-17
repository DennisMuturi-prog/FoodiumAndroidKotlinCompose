package com.example.foodium.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodium.models.UserIntake

@Composable
fun UserIntakeItem(userIntake: UserIntake, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image (small thumbnail)
            AsyncImage(
                model = userIntake.imageUrl,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray, RoundedCornerShape(50.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // Recipe Name
                Text(
                    text = userIntake.recipeName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Created At
                Text(
                    text = "Created: ${userIntake.createdAt}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
