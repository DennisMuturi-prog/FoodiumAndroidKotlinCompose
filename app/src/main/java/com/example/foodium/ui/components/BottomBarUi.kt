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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.foodium.R
import com.example.foodium.navigation.RootGraph
import com.example.foodium.navigation.ScreenRoutes

data class TopLevelRoute(val name: String, val route: String, val icon: ImageVector?=null,
    val painterIconId:Int?=null)

@Composable
fun BottomBarUi(modifier: Modifier = Modifier, navController: NavHostController) {
    val topLevelRoutes = listOf(
        TopLevelRoute(name="Home", route=ScreenRoutes.KenyanRecipesScreen.route, icon=Icons.Default.Home),
        TopLevelRoute(name="CNN", route=ScreenRoutes.CNNScreen.route, painterIconId = R.drawable.image_search_24dp_e8eaed_fill0_wght400_grad0_opsz24),
        TopLevelRoute(name="Scanner", route=ScreenRoutes.BarcodeScannerScreen.route, painterIconId = R.drawable.barcode_reader_24dp_e8eaed_fill0_wght400_grad0_opsz24)

    )
    val homeGraphRoutes = listOf(RootGraph.WorldwideRecipes.name,RootGraph.KenyanRecipes.name)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = topLevelRoutes.any { it.route == currentDestination?.route } || homeGraphRoutes.contains(currentDestination?.route)
    if (bottomBarDestination) {
        NavigationBar {

            topLevelRoutes.forEach { topLevelRoute ->
                NavigationBarItem(
                    icon = {
                        if(topLevelRoute.icon!=null){
                            Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name)

                        }else if(topLevelRoute.painterIconId!=null){
                            Icon(painter= painterResource(topLevelRoute.painterIconId), contentDescription = topLevelRoute.name)

                        }

                    },
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

