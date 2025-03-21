package com.example.foodium.navigation
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodium.domain.Classification
import com.example.foodium.serverSentEvents.NewReviewsViewModel
import com.example.foodium.ui.components.ChartsVisualization
import com.example.foodium.ui.components.KenyanRecipeInfo
import com.example.foodium.ui.screens.BarCodeScannerScreen
import com.example.foodium.ui.screens.FoodClassifierScreen
import com.example.foodium.ui.screens.IntakeVisualizations
import com.example.foodium.ui.screens.KenyanRecipeIntakeScreen
import com.example.foodium.ui.screens.KenyanRecipesScreen
import com.example.foodium.ui.screens.RecipeInfo
import com.example.foodium.ui.screens.RecipeIntakeScreen
import com.example.foodium.ui.screens.RecipesViewModel
import com.example.foodium.ui.screens.SearchScreen
import com.example.foodium.ui.screens.WorldwideRecipesScreen
import com.example.foodium.ui.viewmodels.OpenFoodFactsViewModel
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel

@Composable
fun HomeNavGraph(
    navController:NavHostController,
    modifier:Modifier=Modifier,
    recipesViewModel: RecipesViewModel,
    openFoodFactsViewModel: OpenFoodFactsViewModel,
    controller: LifecycleCameraController,
    classifications:List<Classification>,
    newReviewsViewModel: NewReviewsViewModel,
    recipeIntakeViewModel: RecipeIntakeViewModel

) {
    NavHost(
        navController = navController,
        route = ScreenRoutes.HomeNav.route,
        startDestination = ScreenRoutes.KenyanRecipesScreen.route
    ) {
        composable(route = ScreenRoutes.KenyanRecipesScreen.route){
            KenyanRecipesScreen(recipesViewModel = recipesViewModel, modifier = modifier, onKenyanRecipeInfoClick ={
                navController.navigate(ScreenRoutes.KenyanRecipeInfoScreen.route)
            } )
        }

        composable(route = ScreenRoutes.WorldRecipesScreen.route){
            WorldwideRecipesScreen(recipesViewModel = recipesViewModel, modifier = modifier, onRecipeInfoClick = {
                navController.navigate(ScreenRoutes.RecipeInfoScreen.route)
            })
        }

        composable(route = ScreenRoutes.SearchScreen.route){
            SearchScreen(recipesViewModel = recipesViewModel, modifier = modifier)
        }
        composable(route = ScreenRoutes.BarcodeScannerScreen.route){
            BarCodeScannerScreen(openFoodFactsViewModel = openFoodFactsViewModel, modifier = modifier)
        }
        composable(route = ScreenRoutes.CNNScreen.route){
            FoodClassifierScreen(controller=controller, classifications = classifications, modifier = modifier)
        }
        composable(route = ScreenRoutes.RecipeInfoScreen.route){
            RecipeInfo(recipesViewModel = recipesViewModel, modifier = modifier, newReviewsViewModel =newReviewsViewModel )
        }
        composable(route = ScreenRoutes.KenyanRecipeInfoScreen.route){
            KenyanRecipeInfo(recipesViewModel = recipesViewModel, modifier = modifier, newReviewsViewModel =newReviewsViewModel)
        }
        composable(route= ScreenRoutes.WorldwideRecipeIntakeScreen.route) {
            RecipeIntakeScreen(modifier=modifier, recipeIntakeViewModel =recipeIntakeViewModel, moveToRecipeInfoScreen = {
                recipesViewModel.changeCurrentRecipe(it)
                navController.navigate(ScreenRoutes.RecipeInfoScreen.route)
            } )
        }
        composable(route= ScreenRoutes.KenyanRecipeIntakeScreen.route) {
            KenyanRecipeIntakeScreen(modifier=modifier, recipeIntakeViewModel =recipeIntakeViewModel, moveToKenyanRecipeInfoScreen = {
                recipesViewModel.changeCurrentKenyanRecipe(it)
                navController.navigate(ScreenRoutes.KenyanRecipeInfoScreen.route)
            } )
        }
        composable(route=ScreenRoutes.IntakeVisualizationsScreen.route) {
            IntakeVisualizations(modifier=modifier, recipeIntakeViewModel = recipeIntakeViewModel, moveToRecipeInfoScreen = {
                recipesViewModel.changeCurrentRecipe(it)
                navController.navigate(ScreenRoutes.RecipeInfoScreen.route)
            }, moveToKenyanRecipeInfoScreen = {
                recipesViewModel.changeCurrentKenyanRecipe(it)
                navController.navigate(ScreenRoutes.KenyanRecipeInfoScreen.route)
            })
        }
        composable(route=ScreenRoutes.ChartsVisualization.route) {
            ChartsVisualization(modifier=modifier)

        }

    }
}