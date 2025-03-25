package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.ui.components.KenyanRecipeCard
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.example.foodium.models.KenyanRecipe
import kotlinx.coroutines.flow.Flow


@Composable
fun KenyanRecipesScreen(modifier: Modifier = Modifier,recipesViewModel: RecipesViewModel,kenyanRecipesFlow:Flow<PagingData<KenyanRecipe>>,onKenyanRecipeInfoClick:()->Unit) {
//    val lazyListState= rememberLazyListState()
    val kenyanRecipes = kenyanRecipesFlow.collectAsLazyPagingItems()
    LazyColumn(modifier=modifier.fillMaxSize()) {
        items(kenyanRecipes.itemCount) { index ->
            kenyanRecipes[index]?.let {recipe->
                KenyanRecipeCard(recipe=recipe,onRecipeInfoClick={
                    recipesViewModel.changeCurrentKenyanRecipe(it)
                    onKenyanRecipeInfoClick()
                })
            }

        }
        when (val state = kenyanRecipes.loadState.refresh) { //FIRST LOAD
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

        when (val state = kenyanRecipes.loadState.append) { // Pagination
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


