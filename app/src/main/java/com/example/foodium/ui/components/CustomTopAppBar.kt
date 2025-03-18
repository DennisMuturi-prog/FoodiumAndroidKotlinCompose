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
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodium.navigation.ScreenRoutes
import com.example.foodium.ui.screens.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController:NavHostController,authViewModel: AuthViewModel,scrollBehavior: TopAppBarScrollBehavior,logout:()->Unit) {
    val topLevelRoutes= listOf(ScreenRoutes.KenyanRecipesScreen.route,ScreenRoutes.WorldRecipesScreen.route,ScreenRoutes.WorldwideRecipeIntakeScreen.route,ScreenRoutes.KenyanRecipeIntakeScreen.route,ScreenRoutes.IntakeVisualizationsScreen.route)
    val intakeRoutes= listOf(ScreenRoutes.WorldwideRecipeIntakeScreen.route,ScreenRoutes.KenyanRecipeIntakeScreen.route,ScreenRoutes.IntakeVisualizationsScreen.route)
    val navBackStackEntry by  navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = topLevelRoutes.any { it == currentDestination?.route }
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
                if(intakeRoutes.contains(currentDestination?.route)){
                    Row {
                        TextButton(onClick = {
                            navController.navigate(ScreenRoutes.IntakeVisualizationsScreen.route){
                                navController.graph.startDestinationRoute?.let { route->
                                    popUpTo(route) {
                                        saveState =true
                                    }
                                    launchSingleTop=true
                                    restoreState=true

                                }
                            }
                        }) {
                            Text(text="visualize",
                                fontWeight = if(currentDestination?.route==ScreenRoutes.IntakeVisualizationsScreen.route) FontWeight.Bold else FontWeight.Normal,
                                textDecoration = if(currentDestination?.route==ScreenRoutes.IntakeVisualizationsScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                fontSize = if(currentDestination?.route==ScreenRoutes.IntakeVisualizationsScreen.route) 18.sp else 15.sp)



                        }
                        TextButton(onClick = {
                            navController.navigate(ScreenRoutes.KenyanRecipeIntakeScreen.route){
                                navController.graph.startDestinationRoute?.let { route->
                                    popUpTo(route) {
                                        saveState =true
                                    }
                                    launchSingleTop=true
                                    restoreState=true

                                }
                            }
                        }) {
                            Text(text="kenyan recipes intake",
                                fontWeight = if(currentDestination?.route==ScreenRoutes.KenyanRecipeIntakeScreen.route) FontWeight.Bold else FontWeight.Normal,
                                textDecoration = if(currentDestination?.route==ScreenRoutes.KenyanRecipeIntakeScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                fontSize = if(currentDestination?.route==ScreenRoutes.KenyanRecipeIntakeScreen.route) 18.sp else 15.sp)
                        }
                        TextButton(onClick = {
                            navController.navigate(ScreenRoutes.WorldwideRecipeIntakeScreen.route){
                                navController.graph.startDestinationRoute?.let { route->
                                    popUpTo(route) {
                                        saveState =true
                                    }
                                    launchSingleTop=true
                                    restoreState=true

                                }
                            }
                        }) {
                            Text("world recipes intake",
                                fontWeight = if(currentDestination?.route==ScreenRoutes.WorldwideRecipeIntakeScreen.route) FontWeight.Bold else FontWeight.Normal,
                                textDecoration = if(currentDestination?.route==ScreenRoutes.WorldwideRecipeIntakeScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                fontSize = if(currentDestination?.route==ScreenRoutes.WorldwideRecipeIntakeScreen.route) 18.sp else 15.sp)
                        }
                        TextButton(onClick = {}) {
                            Text("foods")
                        }
                    }

                }else{
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
                            Text("kenyan recipes",
                                fontWeight = if(currentDestination?.route==ScreenRoutes.KenyanRecipesScreen.route) FontWeight.Bold else FontWeight.Normal,
                                textDecoration = if(currentDestination?.route==ScreenRoutes.KenyanRecipesScreen.route)  TextDecoration.Underline else  TextDecoration.None)
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
                            Text("worldwide recipes",
                                fontWeight = if(currentDestination?.route==ScreenRoutes.WorldRecipesScreen.route) FontWeight.Bold else FontWeight.Normal,
                                textDecoration = if(currentDestination?.route==ScreenRoutes.WorldRecipesScreen.route)  TextDecoration.Underline else  TextDecoration.None)
                        }
                        TextButton(onClick = {}) {
                            Text("foods")
                        }
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