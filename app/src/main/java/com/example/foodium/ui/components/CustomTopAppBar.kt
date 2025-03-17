package com.example.foodium.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodium.navigation.RootGraph
import com.example.foodium.navigation.ScreenRoutes
import com.example.foodium.ui.screens.AuthState
import com.example.foodium.ui.screens.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(modifier: Modifier = Modifier,navController:NavHostController,authViewModel: AuthViewModel,scrollBehavior: TopAppBarScrollBehavior,logout:()->Unit) {
    val topLevelRoutes= listOf(ScreenRoutes.KenyanRecipesScreen.route,ScreenRoutes.WorldRecipesScreen.route)
    val navBackStackEntry by  navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = topLevelRoutes.any { it == currentDestination?.route }
    val authState = authViewModel.authState.observeAsState()
//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.NotAuthenticated -> navController.navigate(RootGraph.Login.name) {
//                            popUpTo(route = RootGraph.HomeGraph.name) {
//                                inclusive = true
//                            }
//                        }
//            else -> Unit
//        }
//    }
    if(bottomBarDestination){
//        if(currentDestination?.route==RootGraph.RecipeInfo.name){
//            TopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                ),
//                title = {
//                    Text("recipe details")
//                },
//                actions = {
//                    IconButton(onClick = { /* do something */ }) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Localized description"
//                        )
//                    }
//
//                },
//                navigationIcon = {
//                    IconButton(onClick = { /* do something */ }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "go back"
//                        )
//                    }
//                },
//
//            )
//
//
//        }
        MediumTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Row {
                    TextButton(onClick = {
                        navController.navigate(ScreenRoutes.KenyanRecipesScreen.route){
                            navController.graph.startDestinationRoute?.let { route->
                                popUpTo(route) {
                                    saveState =true
                                }
                                launchSingleTop=true
                                restoreState=true

                            }
                        }
                    }) {
                        Text("kenyan recipes")
                    }
                    TextButton(onClick = {
                        navController.navigate(ScreenRoutes.WorldRecipesScreen.route){
                            navController.graph.startDestinationRoute?.let { route->
                                popUpTo(route) {
                                    saveState =true
                                }
                                launchSingleTop=true
                                restoreState=true

                            }
                        }
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
                    navController.navigate(ScreenRoutes.SearchScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search recipes"
                    )
                }
                TextButton(onClick = {
                    authViewModel.logout()
                    logout()
                }) {
                    Text("logout")
                }
            },
            scrollBehavior = scrollBehavior
        )

    }


}