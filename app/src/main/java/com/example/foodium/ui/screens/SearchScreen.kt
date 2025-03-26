package com.example.foodium.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodium.models.toWorldwideRecipe
import com.example.foodium.ui.components.ChatgptRecipeCard
import com.example.foodium.ui.components.KenyanRecipeCard
import com.example.foodium.ui.components.RecipesSearchBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    recipesViewModel: RecipesViewModel,
    onKenyanRecipeInfoClick: () -> Unit,
    onRecipeInfoClick: () -> Unit,
    onFoodInfoClick: () -> Unit
) {
    val searchResults by recipesViewModel.searchResults.collectAsStateWithLifecycle()
    val foodSearchResults by recipesViewModel.searchFoodResults.collectAsStateWithLifecycle()
    val worldwideSearchResults by recipesViewModel.worldwideSearchResults.collectAsStateWithLifecycle()



    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        RecipesSearchBar(searchResults = searchResults,
            onSearchQueryChange = { recipesViewModel.onSearchQueryChange(it) },
            searchQuery = recipesViewModel.searchQuery,
            worldwideSearchResults = worldwideSearchResults,
            foodSearchResults = foodSearchResults,
            onKenyanRecipeInfoClick = {
                recipesViewModel.changeCurrentKenyanRecipe(it)
                onKenyanRecipeInfoClick()

            },
            onRecipeInfoClick = {
                recipesViewModel.changeCurrentRecipe(it)
                onRecipeInfoClick()

            },
            onFoodInfoClick = {
                recipesViewModel.changeCurrentRecipe(it)
                onFoodInfoClick()
            })
        Spacer(Modifier.height(10.dp))
        LazyColumn(modifier = modifier.fillMaxSize()) {
            /* stickyHeader {


             }*/
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].uuid },
                itemContent = { index ->
                    val recipe = searchResults[index]
                    KenyanRecipeCard(recipe = recipe, onRecipeInfoClick = {
                        recipesViewModel.changeCurrentKenyanRecipe(it)
                        onKenyanRecipeInfoClick()
                    })
                }
            )
            items(
                count = worldwideSearchResults.size,
                key = { index -> worldwideSearchResults[index].uuid },
                itemContent = { index ->
                    val recipe = worldwideSearchResults[index]
                    ChatgptRecipeCard(recipe = recipe, onRecipeInfoClick = {
                        recipesViewModel.changeCurrentRecipe(it)
                        onRecipeInfoClick()
                    })
                }
            )
            items(
                count = foodSearchResults.size,
                key = { index -> foodSearchResults[index].uuid },
                itemContent = { index ->
                    val food = foodSearchResults[index]
                    TextButton(
                        onClick = {
                            recipesViewModel.changeCurrentRecipe(food.toWorldwideRecipe())
                            onFoodInfoClick()

                        }

                    ) {
                        Text(food.foodName)
                    }
                }
            )


        }
    }
}