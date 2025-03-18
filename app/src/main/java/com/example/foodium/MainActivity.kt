package com.example.foodium


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodium.navigation.RootNav
import com.example.foodium.ui.components.snackbarconfig.ObserveAsEvents
import com.example.foodium.ui.components.snackbarconfig.SnackbarController
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.RecipesViewModel
import com.example.foodium.ui.theme.FoodiumTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if(!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }
        enableEdgeToEdge()
        setContent {

            val authViewModel=viewModel<AuthViewModel>(
                factory = viewModelFactory {
                    AuthViewModel(MyApplication.appContainer.repository)
                }
            )

            FoodiumTheme {
                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                val scope = rememberCoroutineScope()
                ObserveAsEvents(
                    flow = SnackbarController.events,
                    snackbarHostState
                ) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()

                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Short
                        )

                        if(result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }
                RootNav(authViewModel=authViewModel,snackbarHostState=snackbarHostState)
//                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
//                Scaffold(
//                    topBar = {
//                        CustomTopAppBar(navController=navController, authViewModel = authViewModel, scrollBehavior = scrollBehavior)
//                    },
//                    snackbarHost = {
//                        SnackbarHost(
//                            hostState = snackbarHostState
//                        )
//                    },
//                    modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
//                    bottomBar = {
//                        BottomBarUi(navController=navController)
//                    }
//                ) { innerPadding ->
//                   AppNavigation(modifier=Modifier.padding(innerPadding),authViewModel=authViewModel, navController = navController,recipesViewModel=recipesViewModel,controller=controller,classifications=classifications,
//                       openFoodFactsViewModel = openFoodFactsViewModel, newReviewsViewModel = newReviewsViewModel)
//                }
            }
        }
    }
    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

}


