package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.foodium.models.WorldwideRecipe

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