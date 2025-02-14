package com.example.foodium.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen

@Composable
fun LandingScreen(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    val authResult=authViewModel.authState.observeAsState()
    when(val result=authResult.value){
        is RegisterUiState.Error->navController.navigate(FoodiumAppScreen.Signup.name)
        is RegisterUiState.Loading-> Text("loading")
        is RegisterUiState.Success->navController.navigate(FoodiumAppScreen.Home.name)
        null->{}
    }
    Text(modifier=modifier,text="landing screen")
}