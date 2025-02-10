package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData

@Composable
fun Login(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController:NavController) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val authResult=authViewModel.authState.observeAsState()
    Column {
        OutlinedTextField(
            value=username,
            onValueChange = {username=it},
        )

        OutlinedTextField(
            value=password,
            onValueChange = {password=it},
        )
        Button(onClick = {
            val result=authViewModel.loginUser(LoginData(username, password))
            Log.d("register response", result.toString())
        }) {
            Text("Login")
        }
        when(val result=authResult.value){
            is RegisterUiState.Error->Text(text=result.message)
            is RegisterUiState.Loading->Text("loading")
            is RegisterUiState.Success->navController.navigate(FoodiumAppScreen.UserPreferences.name)
            is RegisterUiState.InitialAuth->{}
            null->{}
        }
        TextButton(onClick = {
            navController.navigate(FoodiumAppScreen.Signup.name)
        }) {
            Text(text = "Don't have an account, sign up")
        }
    }



}