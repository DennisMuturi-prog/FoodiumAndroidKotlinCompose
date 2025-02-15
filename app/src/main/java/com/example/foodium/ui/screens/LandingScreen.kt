package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen

@Composable
fun LandingScreen(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    val authState=authViewModel.landingAuthState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.NotAuthenticated -> navController.navigate(FoodiumAppScreen.Login.name)
            is AuthState.Success ->navController.navigate(FoodiumAppScreen.Home.name)
            is AuthState.Error ->navController.navigate(FoodiumAppScreen.Login.name)
            else -> Unit
        }
    }
    Column(modifier=modifier) {
        when(val result=authState.value){
            is AuthState.Error->Text(result.message)
            is AuthState.Loading-> Text("loading")
            is AuthState.Success->{}
            is AuthState.NotAuthenticated->{}
            null->{}
        }
        Text(text="landing screen")

    }

}