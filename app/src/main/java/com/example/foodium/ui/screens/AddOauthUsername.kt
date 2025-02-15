package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val updateUsernameState=authViewModel.updateUsernameState.observeAsState()
    LaunchedEffect(updateUsernameState.value) {
        when(updateUsernameState.value){
            is AuthState.Success ->navController.navigate(FoodiumAppScreen.UserPreferences.name)
            else -> Unit
        }
    }
    Column(modifier=modifier) {
        OutlinedTextField(value=username,
            onValueChange = {username=it})
        when(val result=updateUsernameState.value){
            is AuthState.Error-> Text(text=result.message)
            is AuthState.Loading-> Text("loading")
            is AuthState.Success->{}
            is AuthState.NotAuthenticated->{}
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