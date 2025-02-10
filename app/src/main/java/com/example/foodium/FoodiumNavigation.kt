package com.example.foodium

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.foodium.ui.screens.AddOauthUsername
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.Register
import com.example.foodium.ui.screens.UserPreferences
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.foodium.network.RegisterResponse
import com.example.foodium.ui.screens.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class FoodiumAppScreen() {
    Signup,
    Login,
    OauthUsername,
    UserPreferences

}
@Composable
fun FoodiumNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel,preferencesDataStore: FoodiumPreferencesStore) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    NavHost(navController, startDestination = FoodiumAppScreen.Signup.name){
        composable(route=FoodiumAppScreen.Signup.name){
            Register(modifier=modifier,authViewModel=authViewModel,navController=navController)
        }
        composable(route=FoodiumAppScreen.Login.name){
            Login(authViewModel=authViewModel,navController=navController)
        }
        composable(route=FoodiumAppScreen.OauthUsername.name){
            AddOauthUsername(authViewModel=authViewModel,navController=navController)
        }
        composable(route=FoodiumAppScreen.UserPreferences.name){
            UserPreferences(authViewModel=authViewModel,navController=navController)
        }
        composable(
            route = "addUsername?accessToken={accessToken}&refreshToken={refreshToken}",
            deepLinks = listOf(navDeepLink {
                uriPattern = "foodiumapp://oauth?accessToken={accessToken}&refreshToken={refreshToken}"
            }),
            arguments = listOf(
                navArgument("accessToken") { type = NavType.StringType; defaultValue = "" },
                navArgument("refreshToken") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val accessToken = backStackEntry.arguments?.getString("accessToken") ?: ""
            val refreshToken = backStackEntry.arguments?.getString("refreshToken") ?: ""
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    preferencesDataStore.saveString("accessToken", accessToken)
                    preferencesDataStore.saveString("refreshToken", refreshToken)
                }
            }
            authViewModel.addTokensToStore(RegisterResponse(accessToken,refreshToken))
            AddOauthUsername(authViewModel=authViewModel, navController = navController)
        }


    }



}