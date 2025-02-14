package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen

@Composable
fun Home(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    val authState=authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.NotAuthenticated -> navController.navigate(FoodiumAppScreen.Login.name)
            else -> Unit
        }
    }
    Column(modifier=modifier) {
        Text(
            text = "Home",
        )
        Button(onClick = {
            authViewModel.logout()
        }) {
            Text("log out")

        }


    }

}