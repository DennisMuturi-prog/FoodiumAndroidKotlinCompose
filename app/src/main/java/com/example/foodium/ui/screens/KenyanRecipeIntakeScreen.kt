package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.ui.components.UserIntakeItem
import com.example.foodium.ui.components.UserKenyanRecipeIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel

@Composable
fun KenyanRecipeIntakeScreen(modifier: Modifier = Modifier, recipeIntakeViewModel: RecipeIntakeViewModel,moveToKenyanRecipeInfoScreen:(KenyanRecipe)->Unit) {
    val userKenyanRecipeIntake=recipeIntakeViewModel.kenyanRecipeIntake.collectAsLazyPagingItems()
//    LaunchedEffect(Unit) {
//        recipeIntakeViewModel.attachDataSource()
//
//    }
    LazyColumn(
        modifier=modifier.fillMaxSize()
    ) {
        items(userKenyanRecipeIntake.itemCount){index->
            userKenyanRecipeIntake[index]?.let { intake->
                UserKenyanRecipeIntakeItem(userIntake = intake,moveToKenyanRecipeInfoScreen={moveToKenyanRecipeInfoScreen(it)})
            }
        }

    }



}