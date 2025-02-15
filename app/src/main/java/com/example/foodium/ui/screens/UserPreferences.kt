package com.example.foodium.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.foodium.ui.components.DietTypeSelection

@Composable
fun UserPreferences(modifier: Modifier = Modifier,authViewModel: AuthViewModel,navController: NavController) {
    var dietType by remember {
        mutableStateOf("")
    }
    Column(
        modifier=modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text=dietType)
        DietTypeSelection (onDietTypeSelect = {dietType=it})
    }

}