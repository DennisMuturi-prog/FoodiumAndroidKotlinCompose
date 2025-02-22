package com.example.foodium

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.foodium.data.TfLiteFoodClassifier
import com.example.foodium.domain.Classification
import com.example.foodium.navigation.AppNavigation
import com.example.foodium.presentation.FoodImageAnalyzer
import com.example.foodium.ui.components.BottomBarUi
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.RecipesViewModel
import com.example.foodium.ui.theme.FoodiumTheme

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
            var classifications by remember {
                mutableStateOf(emptyList<Classification>())
            }
            val analyzer = remember {
                FoodImageAnalyzer(
                    classifier = TfLiteFoodClassifier(
                        context = applicationContext
                    ),
                    onResults = {
                        classifications = it
                    }
                )
            }
            val controller = remember {
                LifecycleCameraController(applicationContext).apply {
                    setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                    setImageAnalysisAnalyzer(
                        ContextCompat.getMainExecutor(applicationContext),
                        analyzer
                    )
                }
            }

            val authViewModel=viewModel<AuthViewModel>(
                factory = viewModelFactory {
                    AuthViewModel(MyApplication.appContainer.repository)
                }
            )
            val recipesViewModel=viewModel<RecipesViewModel>(
                factory = viewModelFactory {
                    RecipesViewModel(MyApplication.appContainer.repository)
                }
            )
            val navController = rememberNavController()
            FoodiumTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBarUi(navController=navController)
                    }
                ) { innerPadding ->
                   AppNavigation(modifier=Modifier.padding(innerPadding),authViewModel=authViewModel, navController = navController,recipesViewModel=recipesViewModel,controller=controller,classifications=classifications)
                }
            }
        }
    }
    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

}


