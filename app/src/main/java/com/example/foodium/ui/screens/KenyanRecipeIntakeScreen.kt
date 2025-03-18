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
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.ui.components.UserKenyanRecipeIntakeItem
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel
import com.example.foodium.utils.getErrorMessage

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
        when (val state = userKenyanRecipeIntake.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item{
                    state.error.message?.let { Text(text= it, color = Color.Red) }
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

        when (val state = userKenyanRecipeIntake.loadState.append) { // Pagination
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