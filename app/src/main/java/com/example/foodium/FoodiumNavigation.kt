package com.example.foodium

//enum class FoodiumAppScreen{
//    LandingScreen,
//    Home,
//    Signup,
//    Login,
//    OauthUsername,
//    UserPreferences
//
//}
//@Composable
//fun FoodiumNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
//    val navController = rememberNavController()
//    NavHost(navController, startDestination = FoodiumAppScreen.LandingScreen.name, builder = {
//        composable(route=FoodiumAppScreen.LandingScreen.name){
//            LandingScreen(modifier=modifier,authViewModel=authViewModel)
//        }
//        composable(route=FoodiumAppScreen.Home.name){
//            Home(modifier=modifier,authViewModel=authViewModel,navController=navController)
//        }
//        composable(route=FoodiumAppScreen.Signup.name){
//            Register(modifier=modifier,authViewModel=authViewModel,navController=navController)
//        }
//        composable(route=FoodiumAppScreen.Login.name){
//            Login(modifier=modifier,authViewModel=authViewModel,navController=navController)
//        }
//        composable(route=FoodiumAppScreen.OauthUsername.name){
//            AddOauthUsername(modifier=modifier,authViewModel=authViewModel,navController=navController)
//        }
//        composable(route=FoodiumAppScreen.UserPreferences.name){
//            UserPreferences(modifier=modifier,authViewModel=authViewModel,navController=navController)
//        }
//        composable(
//            route = "addUsername?accessToken={accessToken}&refreshToken={refreshToken}",
//            deepLinks = listOf(navDeepLink {
//                uriPattern = "foodiumapp://oauth?accessToken={accessToken}&refreshToken={refreshToken}"
//            }),
//            arguments = listOf(
//                navArgument("accessToken") { type = NavType.StringType; defaultValue = "" },
//                navArgument("refreshToken") { type = NavType.StringType; defaultValue = "" }
//            )
//        ) { backStackEntry ->
//            val accessToken = backStackEntry.arguments?.getString("accessToken") ?: ""
//            val refreshToken = backStackEntry.arguments?.getString("refreshToken") ?: ""
//            authViewModel.updatePreferencesDataStore(accessToken,refreshToken)
//            AddOauthUsername(modifier=modifier,authViewModel=authViewModel, navController = navController)
//        }
//
//    })
//}