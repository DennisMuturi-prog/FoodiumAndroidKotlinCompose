package com.example.foodium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.theme.FoodiumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            val authViewModel=viewModel<AuthViewModel>(
                factory = viewModelFactory {
                    AuthViewModel(MyApplication.appContainer.backendApi,MyApplication.appContainer.preferencesDataStore)
                }
            )
            FoodiumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   FoodiumNavigation(modifier=Modifier.padding(innerPadding),authViewModel=authViewModel)
                }
            }
        }
    }

}


