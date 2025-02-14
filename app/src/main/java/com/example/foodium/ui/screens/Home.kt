package com.example.foodium.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun Home(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    Text(
        text = "Home",
        modifier = modifier
    )
}