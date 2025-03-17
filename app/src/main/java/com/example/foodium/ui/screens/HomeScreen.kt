package com.example.foodium.ui.screens

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodium.MyApplication
import com.example.foodium.data.TfLiteFoodClassifier
import com.example.foodium.domain.Classification
import com.example.foodium.navigation.HomeNavGraph
import com.example.foodium.presentation.FoodImageAnalyzer
import com.example.foodium.serverSentEvents.NewReviewsViewModel
import com.example.foodium.ui.components.BottomBarUi
import com.example.foodium.ui.components.CustomTopAppBar
import com.example.foodium.ui.components.snackbarconfig.ObserveAsEvents
import com.example.foodium.ui.components.snackbarconfig.SnackbarController
import com.example.foodium.ui.theme.FoodiumTheme
import com.example.foodium.ui.viewmodels.OpenFoodFactsViewModel
import com.example.foodium.viewModelFactory
import kotlinx.coroutines.launch

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
//    val recipesViewModel = viewModel<RecipesViewModel>(
//        factory = viewModelFactory {
//            RecipesViewModel(MyApplication.appContainer.repository)
//        }
//    )
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
    val navController = rememberNavController()
//    val snackbarHostState = remember {
//        SnackbarHostState()
//    }
//    val scope = rememberCoroutineScope()
//    ObserveAsEvents(
//        flow = SnackbarController.events,
//        snackbarHostState
//    ) { event ->
//        scope.launch {
//            snackbarHostState.currentSnackbarData?.dismiss()
//
//            val result = snackbarHostState.showSnackbar(
//                message = event.message,
//                actionLabel = event.action?.name,
//                duration = SnackbarDuration.Short
//            )
//
//            if (result == SnackbarResult.ActionPerformed) {
//                event.action?.action?.invoke()
//            }
//        }
//    }
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
            classifications = classifications
        )
    }
}