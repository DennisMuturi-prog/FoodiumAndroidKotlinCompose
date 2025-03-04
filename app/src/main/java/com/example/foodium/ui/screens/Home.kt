package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.foodium.ui.components.RecipeCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.ui.components.ChatgptRecipeCard
import com.example.foodium.ui.components.KenyanRecipeCard
import com.example.foodium.ui.components.RecipesSearchBar

@Composable
fun Home(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    recipesViewModel: RecipesViewModel,
    onLogout: () -> Unit,
    onRecipeInfoClick:()->Unit,
    onKenyanRecipeInfoClick:()->Unit
    ) {
    var currentRegion by remember {
        mutableStateOf("Kenyan")
    }
    val recipes = recipesViewModel.recipes.collectAsLazyPagingItems()
    val kenyanRecipes = recipesViewModel.kenyanRecipes.collectAsLazyPagingItems()
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.NotAuthenticated -> onLogout()
            else -> Unit
        }
    }
    val searchResults by recipesViewModel.searchResults.collectAsStateWithLifecycle()




    Column(modifier = modifier) {
        Row {
            Text("Home")
            Button(onClick = {
                authViewModel.logout()
            }) {
                Text("log out")

            }

        }
        RecipesSearchBar(searchResults =searchResults, searchQuery = recipesViewModel.searchQuery, onSearchQueryChange = {recipesViewModel.onSearchQueryChange(it)})
        Row {
            TextButton (onClick = {
                currentRegion="Kenyan"

            }) {
                Text("Kenyan recipes")
            }
            TextButton (onClick = {
                currentRegion="Worldwide"

            }) {
                Text("worldwide recipes")
            }

        }
        if(currentRegion=="Kenyan"){
            LazyColumn {
                items(kenyanRecipes.itemCount) { index ->
                    kenyanRecipes[index]?.let {recipe->
                        KenyanRecipeCard(recipe=recipe,onRecipeInfoClick={
                            recipesViewModel.changeCurrentKenyanRecipe(it)
                            onKenyanRecipeInfoClick()
                        })
                    }

                }
            }

        }else{
            LazyColumn {
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


    }

}