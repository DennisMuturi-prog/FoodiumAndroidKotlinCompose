package com.example.foodium.ui.alternativeScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodium.ui.screens.AddHealthAttributes
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.Login

@Composable
fun LoginScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel, onSuccessAuthentication:()->Unit,navigateToSignUp:()->Unit,
                snackbarHostState: SnackbarHostState) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
    ) { innerPadding->
        Login(modifier= Modifier.padding(innerPadding),authViewModel=authViewModel, onSuccessAuthentication=onSuccessAuthentication,navigateToSignUp=navigateToSignUp )
    }

}