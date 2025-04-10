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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.models.toWorldwideRecipeIntake
import com.example.foodium.ui.components.UserIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel

@Composable
fun FoodIntakeScreen(modifier: Modifier = Modifier, recipeIntakeViewModel: RecipeIntakeViewModel, moveToRecipeInfoScreen:(WorldwideRecipe)->Unit) {
    val userFoodIntake=recipeIntakeViewModel.foodIntake.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        recipeIntakeViewModel.attachDataSource()

    }
    LazyColumn(
        modifier=modifier.fillMaxSize()
    ) {
        items(userFoodIntake.itemCount){index->
            userFoodIntake[index]?.let { intake->
                UserIntakeItem(userIntake = intake.toWorldwideRecipeIntake(), moveToRecipeInfoScreen = {moveToRecipeInfoScreen(it)})
            }
        }
        when (val state = userFoodIntake.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item {
                    Text("no intake ,add a bite")
                }
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

        when (val state = userFoodIntake.loadState.append) { // Pagination
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