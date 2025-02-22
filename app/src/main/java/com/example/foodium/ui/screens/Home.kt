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
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.ui.components.ChatgptRecipeCard

@Composable
fun Home(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    recipesViewModel: RecipesViewModel,
    onLogout: () -> Unit,
    ) {
    val recipes = recipesViewModel.recipes.collectAsLazyPagingItems()
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.NotAuthenticated -> onLogout()
            else -> Unit
        }
    }



    Column(modifier = modifier) {
        Row {
            Text(
                text = "Home",
            )
            Button(onClick = {
                authViewModel.logout()
            }) {
                Text("log out")

            }

        }
        LazyColumn {
            items(recipes.itemCount) { index ->
                recipes[index]?.let { recipe ->
                    ChatgptRecipeCard(recipe=recipe)
                }
            }
        }

    }

}