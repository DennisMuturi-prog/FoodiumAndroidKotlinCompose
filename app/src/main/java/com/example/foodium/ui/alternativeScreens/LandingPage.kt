package com.example.foodium.ui.alternativeScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.LandingScreen
import com.example.foodium.ui.screens.Register

@Composable
fun StartScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel, onSuccessAuthentication:()->Unit, onFailedAuthentication:()->Unit) {
    Scaffold { innerPadding->
        LandingScreen(modifier= Modifier.padding(innerPadding),authViewModel=authViewModel, onSuccessAuthentication=onSuccessAuthentication, onFailedAuthentication = onFailedAuthentication)
    }

}