package com.example.foodium

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.foodium.ui.screens.AuthViewModel
import com.example.foodium.ui.screens.Register
import com.example.foodium.ui.theme.FoodiumTheme

class MainActivity : ComponentActivity() {
    private val preferencesDataStore by lazy { FoodiumPreferencesStore(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel=ViewModelProvider(this)[AuthViewModel::class.java]

        setContent {
            FoodiumTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   FoodiumNavigation(modifier=Modifier.padding(innerPadding),authViewModel=authViewModel,preferencesDataStore=preferencesDataStore)
                }
            }
        }
    }

}


