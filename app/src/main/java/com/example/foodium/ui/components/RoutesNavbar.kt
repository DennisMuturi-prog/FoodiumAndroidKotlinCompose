package com.example.foodium.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.foodium.navigation.ScreenRoutes

@Composable
fun RoutesNavBar(modifier: Modifier = Modifier,navigate:()->Unit) {
//    Row {
//        TextButton(onClick = {
//            navigate()
//        }) {
//            Text(text="visualize",
//                fontWeight = if(currentDestination?.route== ScreenRoutes.IntakeVisualizationsScreen.route) FontWeight.Bold else FontWeight.Normal,
//                textDecoration = if(currentDestination?.route== ScreenRoutes.IntakeVisualizationsScreen.route)  TextDecoration.Underline else  TextDecoration.None,
//                fontSize = if(currentDestination?.route== ScreenRoutes.IntakeVisualizationsScreen.route) 18.sp else 15.sp)
//
//
//
//        }
//        TextButton(onClick = {
//            navController.navigate(ScreenRoutes.KenyanRecipeIntakeScreen.route){
//                navController.graph.startDestinationRoute?.let { route->
//                    popUpTo(route) {
//                        saveState =true
//                    }
//                    launchSingleTop=true
//                    restoreState=true
//
//                }
//            }
//        }) {
//            Text(text="kenyan recipes intake",
//                fontWeight = if(currentDestination?.route== ScreenRoutes.KenyanRecipeIntakeScreen.route) FontWeight.Bold else FontWeight.Normal,
//                textDecoration = if(currentDestination?.route== ScreenRoutes.KenyanRecipeIntakeScreen.route)  TextDecoration.Underline else  TextDecoration.None,
//                fontSize = if(currentDestination?.route== ScreenRoutes.KenyanRecipeIntakeScreen.route) 18.sp else 15.sp)
//        }
//        TextButton(onClick = {
//            navController.navigate(ScreenRoutes.WorldwideRecipeIntakeScreen.route){
//                navController.graph.startDestinationRoute?.let { route->
//                    popUpTo(route) {
//                        saveState =true
//                    }
//                    launchSingleTop=true
//                    restoreState=true
//
//                }
//            }
//        }) {
//            Text("world recipes intake",
//                fontWeight = if(currentDestination?.route== ScreenRoutes.WorldwideRecipeIntakeScreen.route) FontWeight.Bold else FontWeight.Normal,
//                textDecoration = if(currentDestination?.route== ScreenRoutes.WorldwideRecipeIntakeScreen.route)  TextDecoration.Underline else  TextDecoration.None,
//                fontSize = if(currentDestination?.route== ScreenRoutes.WorldwideRecipeIntakeScreen.route) 18.sp else 15.sp)
//        }
//        TextButton(onClick = {
//            navController.navigate(ScreenRoutes.ChartsVisualizationScreen.route){
//                navController.graph.startDestinationRoute?.let { route->
//                    popUpTo(route) {
//                        saveState =true
//                    }
//                    launchSingleTop=true
//                    restoreState=true
//
//                }
//            }
//        }) {
//            Text(text="charts",
// `               fontWeight = if(currentDestination?.route== ScreenRoutes.ChartsVisualizationScreen.route) FontWeight.Bold else FontWeight.Normal,
//                textDecoration = if(currentDestination?.route== ScreenRoutes.ChartsVisualizationScreen.route)  TextDecoration.Underline else  TextDecoration.None,
//                fontSize = if(currentDestination?.route== ScreenRoutes.ChartsVisualizationScreen.route) 18.sp else 15.sp)
//        }
//        TextButton(onClick = {}) {
//            Text("foods")
//        }
//    }

}