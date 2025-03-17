package com.example.foodium.ui.alternativeScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.Login
import com.example.foodium.ui.screens.Register

@Composable
fun SignupScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel, onSuccessAuthentication:()->Unit, navigateToLogin:()->Unit) {
    Scaffold { innerPadding->
        Register(modifier= Modifier.padding(innerPadding),authViewModel=authViewModel, onSuccessAuthentication=onSuccessAuthentication,navigateToLogin=navigateToLogin )
    }

}