package com.example.foodium.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodium.navigation.RootGraph
import com.example.foodium.ui.screens.AuthState
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(modifier: Modifier = Modifier,navController:NavHostController,authViewModel: AuthViewModel,scrollBehavior: TopAppBarScrollBehavior) {
    val topLevelRoutes= listOf("Home","RecipeInfo","KenyanRecipeInfo","KenyanRecipes","WorldwideRecipes")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = topLevelRoutes.any { it == currentDestination?.route }
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.NotAuthenticated -> navController.navigate(RootGraph.Login.name) {
                            popUpTo(route = RootGraph.HomeGraph.name) {
                                inclusive = true
                            }
                        }
            else -> Unit
        }
    }
    if(bottomBarDestination){
        MediumTopAppBar(
            title = {
                Row {
                    TextButton(onClick = {
                        navController.navigate(RootGraph.KenyanRecipes.name)
                    }) {
                        Text("kenyan recipes")
                    }
                    TextButton(onClick = {
                        navController.navigate(RootGraph.WorldwideRecipes.name)
                    }) {
                        Text("worldwide recipes")
                    }
                    TextButton(onClick = {}) {
                        Text("foods")
                    }
                }
            },
            navigationIcon = {
                Text("Foodium")

            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(RootGraph.Search.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search recipes"
                    )
                }
                TextButton(onClick = {
                    authViewModel.logout()
                }) {
                    Text("logout")
                }
            },
            scrollBehavior = scrollBehavior
        )

    }


}