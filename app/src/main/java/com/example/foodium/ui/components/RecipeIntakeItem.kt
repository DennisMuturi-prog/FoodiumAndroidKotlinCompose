package com.example.foodium.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.UserIntake
import com.example.foodium.models.UserKenyanIntake
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.models.toKenyanRecipe
import com.example.foodium.models.toWorldwideRecipe

@Composable
fun UserIntakeItem(userIntake: UserIntake, modifier: Modifier = Modifier,moveToRecipeInfoScreen:(WorldwideRecipe)->Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {moveToRecipeInfoScreen(userIntake.toWorldwideRecipe())}
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//             Image (small thumbnail)
//            AsyncImage(
//                model = userIntake.imageUrl,
//                contentDescription = "Recipe Image",
//                modifier = Modifier
//                    .size(64.dp)
//                    .background(Color.Gray, RoundedCornerShape(50.dp))
//            )
            AsyncImage(
                model = userIntake.imageUrl,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape) // Ensures the image is clipped to a circular shape
                    .background(Color.Gray, CircleShape), // Applies background properly
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // Recipe Name
                Text(
                    text = userIntake.recipeName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                // Created At
                Text(
                    text = convertDateToAFormat(userIntake.createdAt),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
@Composable
fun UserKenyanRecipeIntakeItem(userIntake: UserKenyanIntake, modifier: Modifier = Modifier,moveToKenyanRecipeInfoScreen:(KenyanRecipe)->Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {moveToKenyanRecipeInfoScreen(userIntake.toKenyanRecipe())}
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
                    .clip(CircleShape) // Ensures the image is clipped to a circular shape
                    .background(Color.Gray, CircleShape), // Applies background properly
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // Recipe Name
                Text(
                    text = userIntake.recipeName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                // Created At
                Text(
                    text = convertDateToAFormat(userIntake.createdAt),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
