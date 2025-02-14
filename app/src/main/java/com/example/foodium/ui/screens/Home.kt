package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen

@Composable
fun Home(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    val authResult=authViewModel.authState.observeAsState()
    Column(modifier=modifier) {
        Text(
            text = "Home",
        )
        when(val result=authResult.value){
            is RegisterUiState.Error->navController.navigate(FoodiumAppScreen.Login.name)
            is RegisterUiState.Loading->Text("loading")
            is RegisterUiState.Success->{}
            is RegisterUiState.NotAuthenticated->navController.navigate(FoodiumAppScreen.Login.name)
            null->{}
        }
        Button(onClick = {
            authViewModel.logout()
        }) {
            Text("log out")

        }


    }

}