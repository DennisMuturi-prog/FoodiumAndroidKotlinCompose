package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.foodium.ui.components.LoadingCircle

@Composable
fun LandingScreen(modifier: Modifier = Modifier,
                  authViewModel: AuthViewModel,
                  onSuccessAuthentication:()->Unit,
                  onFailedAuthentication:()->Unit) {
    val authState=authViewModel.landingAuthState.observeAsState()
    LaunchedEffect(Unit) {
        authViewModel.getAuthTokensFromServer()
    }
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.NotAuthenticated ->onFailedAuthentication()
            is AuthState.Success ->onSuccessAuthentication()
            is AuthState.Error ->onFailedAuthentication()
            else -> Unit
        }
    }
    Column(modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text="Foodium")
        when(val result=authState.value){
            is AuthState.Error->Text(text=result.message)
            is AuthState.Loading-> LoadingCircle()
            is AuthState.Success->{}
            is AuthState.NotAuthenticated->{}
            null->{}
        }

    }

}