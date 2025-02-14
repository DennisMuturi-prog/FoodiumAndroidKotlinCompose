package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen

@Composable
fun LandingScreen(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    val authResult=authViewModel.authState.observeAsState()
    Column(modifier=modifier) {
        when(val result=authResult.value){
            is RegisterUiState.Error->Text(result.message)
            is RegisterUiState.Loading-> Text("loading")
            is RegisterUiState.Success->navController.navigate(FoodiumAppScreen.Home.name)
            is RegisterUiState.NotAuthenticated->navController.navigate(FoodiumAppScreen.Signup.name)
            null->{}
        }
        Text(text="landing screen")

    }

}