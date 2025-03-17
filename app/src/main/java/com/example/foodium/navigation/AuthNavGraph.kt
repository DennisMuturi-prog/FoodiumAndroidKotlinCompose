package com.example.foodium.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.foodium.ui.alternativeScreens.HealthAttributesScreen
import com.example.foodium.ui.alternativeScreens.LoginScreen
import com.example.foodium.ui.alternativeScreens.OauthUsernameScreen
import com.example.foodium.ui.alternativeScreens.SignupScreen
import com.example.foodium.ui.alternativeScreens.StartScreen
import com.example.foodium.ui.screens.AuthViewModel


fun NavGraphBuilder.AuthNav(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = ScreenRoutes.StartScreen.route,
        route = ScreenRoutes.AuthNav.route
    ) {
        composable(route = ScreenRoutes.StartScreen.route) {
            StartScreen(authViewModel = authViewModel,
                onSuccessAuthentication = { navController.navigate(ScreenRoutes.HomeNav.route) },
                onFailedAuthentication = { navController.navigate(ScreenRoutes.LoginScreen.route) })
        }

        composable(route = ScreenRoutes.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onSuccessAuthentication = {
                    navController.navigate(ScreenRoutes.HomeNav.route)

                },
                navigateToSignUp = {
                    navController.navigate(ScreenRoutes.SignUpScreen.route)
                },
                snackbarHostState = snackbarHostState

            )
        }
        composable(route = ScreenRoutes.SignUpScreen.route) {
            SignupScreen(
                authViewModel = authViewModel,
                onSuccessAuthentication = {
                    navController.navigate(ScreenRoutes.AddHealthAttributesScreen.route)
                },
                navigateToLogin = {
                    navController.navigate(ScreenRoutes.LoginScreen.route)
                },
                snackbarHostState = snackbarHostState
            )
        }
        composable(route = ScreenRoutes.OauthUsernameScreen.route) {
            OauthUsernameScreen(
                authViewModel = authViewModel,
                onSuccessAddUsername = {
                    navController.navigate(ScreenRoutes.AddHealthAttributesScreen.route)
                },
                snackbarHostState = snackbarHostState)
        }
        composable(route = RootGraph.UserPreferences.name) {
            HealthAttributesScreen(
                authViewModel = authViewModel,
                onSuccessAddHealthAttributes = {
                    navController.navigate(ScreenRoutes.HomeNav.route)
                },
                snackbarHostState = snackbarHostState)
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
            OauthUsernameScreen(
                authViewModel = authViewModel,
                onSuccessAddUsername = { navController.navigate(ScreenRoutes.AddHealthAttributesScreen.route) },
                snackbarHostState = snackbarHostState)
        }
    }
}