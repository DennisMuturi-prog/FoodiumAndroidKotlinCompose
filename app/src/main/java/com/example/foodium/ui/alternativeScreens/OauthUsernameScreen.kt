package com.example.foodium.ui.alternativeScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodium.ui.screens.AddOauthUsername
import com.example.foodium.ui.screens.AuthViewModel

@Composable
fun OauthUsernameScreen(modifier: Modifier = Modifier,authViewModel: AuthViewModel,onSuccessAddUsername:()->Unit) {
    Scaffold { innerPadding->
        AddOauthUsername(modifier=Modifier.padding(innerPadding),authViewModel=authViewModel, onSuccessAddUsername =onSuccessAddUsername )
    }

}