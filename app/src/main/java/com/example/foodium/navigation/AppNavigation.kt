package com.example.foodium.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.foodium.ui.screens.AddOauthUsername
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.Home
import com.example.foodium.ui.screens.LandingScreen
import com.example.foodium.ui.screens.Login
import com.example.foodium.ui.screens.Register
import com.example.foodium.ui.screens.AddHealthAttributes
import com.example.foodium.ui.screens.RecipesViewModel


enum class RootGraph {
    LandingScreen,
    Home,
    Signup,
    Login,
    OauthUsername,
    UserPreferences,
    AuthGraph,
    HomeGraph,
    Intake

}


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    recipesViewModel: RecipesViewModel
) {
    NavHost(navController, startDestination = RootGraph.AuthGraph.name) {
        navigation(
            route = RootGraph.AuthGraph.name,
            startDestination = RootGraph.LandingScreen.name
        ) {
            composable(route = RootGraph.LandingScreen.name) {
                LandingScreen(
                    modifier = modifier, authViewModel = authViewModel,
                    onSuccessAuthentication = {
                        navController.navigate(RootGraph.HomeGraph.name) {
                            popUpTo(route = RootGraph.AuthGraph.name) {
                                inclusive = true
                            }
                        }
                    },
                    onFailedAuthentication = { navController.navigate(RootGraph.Login.name) }

                )
            }
            composable(route = RootGraph.Signup.name) {
                Register(modifier = modifier,
                    authViewModel = authViewModel,
                    onSuccessAuthentication = { navController.navigate(RootGraph.OauthUsername.name) },
                    navigateToLogin = { navController.navigate(RootGraph.Login.name) })
            }
            composable(route = RootGraph.Login.name) {
                Login(modifier = modifier,
                    authViewModel = authViewModel,
                    onSuccessAuthentication = {
                        navController.navigate(RootGraph.HomeGraph.name) {
                            popUpTo(route = RootGraph.AuthGraph.name) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToSignUp = { navController.navigate(RootGraph.Signup.name) })
            }
            composable(route = RootGraph.OauthUsername.name) {
                AddOauthUsername(modifier = modifier,
                    authViewModel = authViewModel,
                    onSuccessAddUsername = {
                        navController.navigate(RootGraph.UserPreferences.name) {
                            popUpTo(route = RootGraph.AuthGraph.name) {
                                inclusive = true
                            }
                        }
                    })
            }
            composable(route = RootGraph.UserPreferences.name) {
                AddHealthAttributes(modifier = modifier,
                    authViewModel = authViewModel,
                    onSuccessAddHealthAttributes = {
                        navController.navigate(RootGraph.HomeGraph.name) {
                            popUpTo(route = RootGraph.AuthGraph.name) {
                                inclusive = true
                            }
                        }
                    })
            }
            composable(
                route = "addUsername?accessToken={accessToken}&refreshToken={refreshToken}",
                deepLinks = listOf(navDeepLink {
                    uriPattern =
                        "foodiumapp://oauth?accessToken={accessToken}&refreshToken={refreshToken}"
                }),
                arguments = listOf(
                    navArgument("accessToken") { type = NavType.StringType; defaultValue = "" },
                    navArgument("refreshToken") { type = NavType.StringType; defaultValue = "" }
                )
            ) { backStackEntry ->
                val accessToken = backStackEntry.arguments?.getString("accessToken") ?: ""
                val refreshToken = backStackEntry.arguments?.getString("refreshToken") ?: ""
                authViewModel.updatePreferencesDataStore(accessToken, refreshToken)
                AddOauthUsername(modifier = modifier,
                    authViewModel = authViewModel,
                    onSuccessAddUsername = { navController.navigate(RootGraph.UserPreferences.name) })
            }

        }
        navigation(startDestination = RootGraph.Home.name, route = RootGraph.HomeGraph.name) {
            composable(route = RootGraph.Home.name) {
                Home(
                    modifier = modifier,
                    authViewModel = authViewModel,
                    onLogout = {
                        navController.navigate(RootGraph.Login.name) {
                            popUpTo(route = RootGraph.HomeGraph.name) {
                                inclusive = true
                            }
                        }
                    },
                    recipesViewModel = recipesViewModel
                )
            }
        }
    }


}