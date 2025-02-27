package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.foodium.ui.components.LoadingCircle

@Composable
fun LandingScreen(modifier: Modifier = Modifier,
                  authViewModel: AuthViewModel,
                  onSuccessAuthentication:()->Unit,
                  onFailedAuthentication:()->Unit) {
    val authState=authViewModel.landingAuthState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.NotAuthenticated ->onFailedAuthentication()
            is AuthState.Success ->onSuccessAuthentication()
            is AuthState.Error ->onFailedAuthentication()
            else -> Unit
        }
    }
    Column(modifier=modifier) {
        Text(text="landing screen")
        when(val result=authState.value){
            is AuthState.Error->Text(text=result.message)
            is AuthState.Loading-> LoadingCircle()
            is AuthState.Success->{}
            is AuthState.NotAuthenticated->{}
            null->{}
        }

    }

}