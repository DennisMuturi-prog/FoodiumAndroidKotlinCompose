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
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.ChatgptRecipeCard
import kotlinx.coroutines.flow.Flow

@Composable
fun WorldwideRecipesScreen(modifier: Modifier = Modifier,recipesViewModel: RecipesViewModel,recipesFlow:Flow<PagingData<WorldwideRecipe>>,onRecipeInfoClick:()->Unit) {
    val recipes = recipesFlow.collectAsLazyPagingItems()
//    val lazyListState= rememberLazyListState()
    LazyColumn(modifier=modifier.fillMaxSize()) {
        items(recipes.itemCount) { index ->
            recipes[index]?.let { recipe ->
                ChatgptRecipeCard(recipe=recipe,onRecipeInfoClick={
                    recipesViewModel.changeCurrentRecipe(it)
                    onRecipeInfoClick()
                })
            }
        }
        when (val state = recipes.loadState.refresh) { //FIRST LOAD
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

        when (val state = recipes.loadState.append) { // Pagination
            is LoadState.Error -> {
                item{
                    state.error.message?.let { Text(text= it, color = Color.Red) }
                }
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