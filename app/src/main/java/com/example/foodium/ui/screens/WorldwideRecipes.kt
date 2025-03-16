package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.ui.components.ChatgptRecipeCard

@Composable
fun WorldwideRecipesScreen(modifier: Modifier = Modifier,recipesViewModel: RecipesViewModel,onRecipeInfoClick:()->Unit) {
    val recipes = recipesViewModel.recipes.collectAsLazyPagingItems()
    LazyColumn(modifier=modifier.fillMaxSize()) {
        items(recipes.itemCount) { index ->
            recipes[index]?.let { recipe ->
                ChatgptRecipeCard(recipe=recipe,onRecipeInfoClick={
                    recipesViewModel.changeCurrentRecipe(it)
                    onRecipeInfoClick()
                })
            }
        }
    }

}