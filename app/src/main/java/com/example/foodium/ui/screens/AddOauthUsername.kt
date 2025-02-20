package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AddOauthUsername(modifier: Modifier = Modifier,
                     authViewModel: AuthViewModel,
                     onSuccessAddUsername:()->Unit) {
    var username by remember {
        mutableStateOf("")
    }
    val updateUsernameState=authViewModel.updateUsernameState.observeAsState()
    LaunchedEffect(updateUsernameState.value) {
        when(updateUsernameState.value){
            is AuthState.Success ->onSuccessAddUsername()
            else -> Unit
        }
    }
    Column(
        modifier=modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ) {
        Text(text="Add username", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value=username,
            onValueChange = {username=it},
            label = {
                Text("username")
            })
        when(val result=updateUsernameState.value){
            is AuthState.Error-> Text(text=result.message)
            is AuthState.Loading-> Text("loading")
            is AuthState.Success->{}
            is AuthState.NotAuthenticated->{}
            null->{}
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            authViewModel.addUsername(username)
            }

        ) {
            Text("add username")
        }

    }


}

