package com.example.foodium.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodium.navigation.RootGraph

data class TopLevelRoute(val name: String, val route: String, val icon: ImageVector)

@Composable
fun BottomBarUi(modifier: Modifier = Modifier, navController: NavHostController) {
    val topLevelRoutes = listOf(
        TopLevelRoute("Home", RootGraph.Home.name, Icons.Default.Home),
        TopLevelRoute("CNN", RootGraph.ImageClassifier.name, Icons.Default.Build),
        TopLevelRoute("Scanner", RootGraph.BarCodeScanner.name, Icons.Default.Info)

    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = topLevelRoutes.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar {

            topLevelRoutes.forEach { topLevelRoute ->
                NavigationBarItem(
                    icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                    label = { Text(topLevelRoute.name) },
                    selected = currentDestination?.hierarchy?.any {
                        it.route == topLevelRoute.route
                    } == true,
                    onClick = {
                        navController.navigate(topLevelRoute.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }

    }

}

