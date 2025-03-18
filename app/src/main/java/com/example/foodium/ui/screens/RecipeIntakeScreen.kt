package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.UserIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel

@Composable
fun RecipeIntakeScreen(modifier: Modifier = Modifier,recipeIntakeViewModel: RecipeIntakeViewModel,moveToRecipeInfoScreen:(WorldwideRecipe)->Unit) {
    val userRecipeIntake=recipeIntakeViewModel.recipeIntake.collectAsLazyPagingItems()
//    LaunchedEffect(Unit) {
//        recipeIntakeViewModel.attachDataSource()
//
//    }
    LazyColumn(
        modifier=modifier.fillMaxSize()
    ) {
        items(userRecipeIntake.itemCount){index->
            userRecipeIntake[index]?.let { intake->
                UserIntakeItem(userIntake = intake, moveToRecipeInfoScreen = {moveToRecipeInfoScreen(it)})
            }
        }

    }



}