package com.example.foodium.navigation

sealed class ScreenRoutes(val route : String) {
    //Screen Routes
    data object StartScreen : ScreenRoutes("start_screen")

    data object LoginScreen : ScreenRoutes("login_screen")

    data object SignUpScreen : ScreenRoutes("signup_screen")

    data object OauthUsernameScreen : ScreenRoutes("oauthUsername_screen")
    data object AddHealthAttributesScreen : ScreenRoutes("healthAttributes_screen")
    data object WorldRecipesScreen : ScreenRoutes("worldRecipes_screen")
    data object KenyanRecipesScreen : ScreenRoutes("kenyanRecipes_screen")
    data object SearchScreen : ScreenRoutes("search_screen")
    data object BarcodeScannerScreen : ScreenRoutes("barcode_screen")
    data object CNNScreen : ScreenRoutes("cnn_screen")
    data object RecipeInfoScreen : ScreenRoutes("recipeInfo_screen")
    data object KenyanRecipeInfoScreen : ScreenRoutes("kenyanRecipeInfo_screen")

    data object KenyanRecipeIntakeScreen : ScreenRoutes("kenyanRecipeIntake_screen")
    data object WorldwideRecipeIntakeScreen : ScreenRoutes("worldwideRecipeIntake_screen")
    data object FoodsScreen : ScreenRoutes("foods_screen")
    data object FoodsIntakeScreen : ScreenRoutes("foodsIntake_screen")

    data object IntakeVisualizationsScreen : ScreenRoutes("intakeVisualizations_screen")

    data object ChartsVisualization:ScreenRoutes("chartsVisualizations_screen")
    data object HomeScreen : ScreenRoutes("home_screen")

    data object ScreenA : ScreenRoutes("screen_a")

    data object ScreenB : ScreenRoutes("screen_b")

    data object LogoutScreen : ScreenRoutes("logout_screen")

    //Graph Routes
    data object AuthNav : ScreenRoutes("AUTH_NAV_GRAPH")

    data object HomeNav : ScreenRoutes("HOME_NAV_GRAPH")
}