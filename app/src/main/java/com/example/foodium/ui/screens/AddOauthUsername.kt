package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.FoodiumAppScreen

@Composable
fun AddOauthUsername(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    val usernameUpdateState=authViewModel.updateUsernameState.observeAsState()
    Column(modifier=modifier) {
        OutlinedTextField(value=username,
            onValueChange = {username=it})
        when(val result=usernameUpdateState.value){
            is UpdateUsernameUiState.Error-> Text(text=result.message)
            is UpdateUsernameUiState.Loading-> Text("loading")
            is UpdateUsernameUiState.Success->navController.navigate(FoodiumAppScreen.UserPreferences.name)
            null->{}
        }
        Button(onClick = {
            authViewModel.addUsername(username)
            }

        ) {
            Text("add username")
        }

    }


}