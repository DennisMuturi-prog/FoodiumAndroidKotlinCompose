package com.example.foodium.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
    val topLevelRoutes= listOf(ScreenRoutes.KenyanRecipesScreen.route,ScreenRoutes.WorldRecipesScreen.route,ScreenRoutes.WorldwideRecipeIntakeScreen.route,ScreenRoutes.KenyanRecipeIntakeScreen.route,ScreenRoutes.IntakeVisualizationsScreen.route,ScreenRoutes.ChartsVisualizationScreen.route,ScreenRoutes.ForYouDietWorldwideRecipesScreen.route,ScreenRoutes.ForYouDietKenyanRecipesScreen.route,ScreenRoutes.FoodInfoScreen.route,ScreenRoutes.RecipeInfoScreen.route,ScreenRoutes.KenyanRecipeInfoScreen.route,ScreenRoutes.FoodsScreen.route,ScreenRoutes.FoodsIntakeScreen.route)
    val intakeRoutes= listOf(ScreenRoutes.WorldwideRecipeIntakeScreen.route,ScreenRoutes.KenyanRecipeIntakeScreen.route,ScreenRoutes.IntakeVisualizationsScreen.route,ScreenRoutes.FoodsIntakeScreen.route)
    val dietRoutes= listOf(ScreenRoutes.ForYouDietWorldwideRecipesScreen.route,ScreenRoutes.ForYouDietKenyanRecipesScreen.route,ScreenRoutes.FoodsScreen.route)
    val navBackStackEntry by  navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = topLevelRoutes.any { it == currentDestination?.route }
    val recipeInfoRoutes = listOf(ScreenRoutes.RecipeInfoScreen.route,ScreenRoutes.KenyanRecipeInfoScreen.route,ScreenRoutes.FoodInfoScreen.route)
    if(bottomBarDestination){
        if(recipeInfoRoutes.contains(currentDestination?.route)){
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text=if(currentDestination?.route==ScreenRoutes.FoodInfoScreen.route) "food details"  else "recipe details")
                },
//                actions = {
//                    IconButton(onClick = { /* do something */ }) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Localized description"
//                        )
//                    }
//
//                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "go back"
                        )
                    }
                },

            )


        }else{
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    if(intakeRoutes.contains(currentDestination?.route)){
                        LazyRow {
                            item {
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
                            }
                            item {
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

                            }
                            item {
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

                            }
                            item {
                                TextButton(onClick = {
                                    navController.navigate(ScreenRoutes.FoodsIntakeScreen.route){
                                        navController.graph.startDestinationRoute?.let { route->
                                            popUpTo(route) {
                                                saveState =true
                                            }
                                            launchSingleTop=true
                                            restoreState=true

                                        }
                                    }
                                }) {
                                    Text(text="foods intake",
                                        fontWeight = if(currentDestination?.route==ScreenRoutes.FoodsIntakeScreen.route) FontWeight.Bold else FontWeight.Normal,
                                        textDecoration = if(currentDestination?.route==ScreenRoutes.FoodsIntakeScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                        fontSize = if(currentDestination?.route==ScreenRoutes.FoodsIntakeScreen.route) 18.sp else 15.sp)
                                }

                            }



                        }

                    }else if(dietRoutes.contains(currentDestination?.route)){
                        LazyRow {
                            item {
                                TextButton(onClick = {
                                    navController.navigate(ScreenRoutes.ForYouDietKenyanRecipesScreen.route){
                                        navController.graph.startDestinationRoute?.let { route->
                                            popUpTo(route) {
                                                saveState =true
                                            }
                                            launchSingleTop=true
                                            restoreState=true

                                        }
                                    }
                                }) {
                                    Text(text="diet kenyan",
                                        fontWeight = if(currentDestination?.route==ScreenRoutes.ForYouDietKenyanRecipesScreen.route) FontWeight.Bold else FontWeight.Normal,
                                        textDecoration = if(currentDestination?.route==ScreenRoutes.ForYouDietKenyanRecipesScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                        fontSize = if(currentDestination?.route==ScreenRoutes.ForYouDietKenyanRecipesScreen.route) 18.sp else 15.sp)



                                }
                            }
                            item {
                                TextButton(onClick = {
                                    navController.navigate(ScreenRoutes.ForYouDietWorldwideRecipesScreen.route){
                                        navController.graph.startDestinationRoute?.let { route->
                                            popUpTo(route) {
                                                saveState =true
                                            }
                                            launchSingleTop=true
                                            restoreState=true

                                        }
                                    }
                                }) {
                                    Text(text="diet worldwide",
                                        fontWeight = if(currentDestination?.route==ScreenRoutes.ForYouDietWorldwideRecipesScreen.route) FontWeight.Bold else FontWeight.Normal,
                                        textDecoration = if(currentDestination?.route==ScreenRoutes.ForYouDietWorldwideRecipesScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                        fontSize = if(currentDestination?.route==ScreenRoutes.ForYouDietWorldwideRecipesScreen.route) 18.sp else 15.sp)
                                }
                            }


                            item {
                                TextButton(onClick = {
                                    navController.navigate(ScreenRoutes.FoodsScreen.route){
                                        navController.graph.startDestinationRoute?.let { route->
                                            popUpTo(route) {
                                                saveState =true
                                            }
                                            launchSingleTop=true
                                            restoreState=true

                                        }
                                    }
                                }) {
                                    Text("recommender",
                                        fontWeight = if(currentDestination?.route==ScreenRoutes.FoodsScreen.route) FontWeight.Bold else FontWeight.Normal,
                                        textDecoration = if(currentDestination?.route==ScreenRoutes.FoodsScreen.route)  TextDecoration.Underline else  TextDecoration.None,
                                        fontSize = if(currentDestination?.route==ScreenRoutes.FoodsScreen.route) 18.sp else 15.sp)
                                }
                            }
                        }


                    } else{
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


}