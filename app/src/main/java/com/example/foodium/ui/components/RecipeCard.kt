package com.example.foodium.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.example.foodium.models.WorldwideRecipe

@Composable
fun RecipeCard(modifier: Modifier = Modifier,recipe:WorldwideRecipe) {
    Card(){
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.recipeName
        )
        Text(
            text = recipe.recipeName
        )



    }


}