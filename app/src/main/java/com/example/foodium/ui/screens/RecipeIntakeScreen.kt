package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.UserIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel
import com.example.foodium.utils.getErrorMessage

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
        when (val state = userRecipeIntake.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                //TODO Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator()
                    }
                }
            }
            else -> {}
        }

        when (val state = userRecipeIntake.loadState.append) { // Pagination
            is LoadState.Error -> {

                //TODO Pagination Error Item
                //state.error to get error message
            }
            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator()
                    }
                }
            }
            else -> {}
        }

    }



}