package com.example.foodium.ui.screens

import androidx.camera.core.ImageAnalysis
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.foodium.MyApplication
import com.example.foodium.data.TfLiteFoodClassifier
import com.example.foodium.domain.Classification
import com.example.foodium.navigation.HomeNavGraph
import com.example.foodium.presentation.FoodImageAnalyzer
import com.example.foodium.serverSentEvents.NewReviewsViewModel
import com.example.foodium.ui.components.BottomBarUi
import com.example.foodium.ui.components.CustomTopAppBar
import com.example.foodium.ui.viewmodels.OpenFoodFactsViewModel
import com.example.foodium.ui.viewmodels.RecipeIntakeViewModel
import com.example.foodium.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    logout: () -> Unit,
    authViewModel: AuthViewModel,
    recipesViewModel: RecipesViewModel,
    snackbarHostState: SnackbarHostState
) {

    var classifications by remember {
        mutableStateOf(emptyList<Classification>())
    }
    val context = LocalContext.current
    val analyzer = remember {
        FoodImageAnalyzer(
            classifier = TfLiteFoodClassifier(
                context = context
            ),
            onResults = {
                classifications = it
            }
        )
    }
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            imageAnalysisOutputImageFormat = ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                analyzer
            )
        }
    }

    val openFoodFactsViewModel = viewModel<OpenFoodFactsViewModel>(
        factory = viewModelFactory {
            OpenFoodFactsViewModel(MyApplication.appContainer.repository)
        }
    )
    val newReviewsViewModel = viewModel<NewReviewsViewModel>(
        factory = viewModelFactory {
            NewReviewsViewModel(
                sseRepository = MyApplication.appContainer.sseRepository,
                repository = MyApplication.appContainer.repository
            )
        }
    )
    val recipeIntakeViewModel= viewModel<RecipeIntakeViewModel>(
        factory = viewModelFactory {
            RecipeIntakeViewModel(
                repository = MyApplication.appContainer.repository
            )
        }
    )

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            CustomTopAppBar(
                navController = navController,
                authViewModel = authViewModel,
                scrollBehavior = scrollBehavior,
                logout = logout
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        bottomBar = {
            BottomBarUi(navController = navController)
        }
    ) {innerPadding->

        HomeNavGraph(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            recipesViewModel = recipesViewModel,
            newReviewsViewModel = newReviewsViewModel,
            openFoodFactsViewModel = openFoodFactsViewModel,
            controller = controller,
            classifications = classifications,
            recipeIntakeViewModel = recipeIntakeViewModel
        )
    }
}