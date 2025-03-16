package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.ui.components.KenyanRecipeCard

@Composable
fun KenyanRecipesScreen(modifier: Modifier = Modifier,recipesViewModel: RecipesViewModel,onKenyanRecipeInfoClick:()->Unit) {
    val kenyanRecipes = recipesViewModel.kenyanRecipes.collectAsLazyPagingItems()
    LazyColumn(modifier=modifier.fillMaxSize()) {
        items(kenyanRecipes.itemCount) { index ->
            kenyanRecipes[index]?.let {recipe->
                KenyanRecipeCard(recipe=recipe,onRecipeInfoClick={
                    recipesViewModel.changeCurrentKenyanRecipe(it)
                    onKenyanRecipeInfoClick()
                })
            }

        }
    }


}