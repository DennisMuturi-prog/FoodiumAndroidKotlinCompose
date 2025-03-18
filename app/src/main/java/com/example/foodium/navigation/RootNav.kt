package com.example.foodium.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.HomeScreen
import com.example.foodium.ui.screens.RecipesViewModel
import com.example.foodium.ui.viewmodels.OpenFoodFactsViewModel

@Composable
fun RootNav(authViewModel: AuthViewModel,
            snackbarHostState: SnackbarHostState
            ) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.AuthNav.route
    ) {
        AuthNav(navController=navController, authViewModel =authViewModel, snackbarHostState = snackbarHostState )
        composable(route = ScreenRoutes.HomeNav.route){
            HomeScreen(
                logout = {
                    navController.navigate(ScreenRoutes.AuthNav.route) {
                        popUpTo(0){}
                    }
                },
                authViewModel = authViewModel,
                snackbarHostState = snackbarHostState
            )
        }
    }
}