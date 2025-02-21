package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.RecipeCard
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun Home(
    modifier: Modifier = Modifier, authViewModel: AuthViewModel,
    onLogout: () -> Unit,
    recipes: LazyPagingItems<WorldwideRecipe>
    ) {
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.NotAuthenticated -> onLogout()
            else -> Unit
        }
    }

    LazyColumn {
        items(recipes.itemCount) { index ->
            recipes[index]?.let { recipe ->
                RecipeCard(recipe=recipe)
            }
        }
    }


    Column(modifier = modifier) {
        Text(
            text = "Home",
        )
        Button(onClick = {
            authViewModel.logout()
        }) {
            Text("log out")

        }


    }

}