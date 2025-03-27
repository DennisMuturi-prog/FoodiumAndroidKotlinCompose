package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodium.network.LoginData
import com.example.foodium.ui.components.GoogleButton
import com.example.foodium.ui.components.LoadingCircle

data class ValidationResults(
    val usernameValidationErrorStatus: Boolean,
    val passwordValidationErrorStatus: Boolean
)

@Composable
fun Login(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSuccessAuthentication: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var usernameValidationError by remember {
        mutableStateOf(false)
    }
    var passwordValidationError by remember {
        mutableStateOf(false)
    }
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Success -> onSuccessAuthentication()
            else -> Unit
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Login", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                if(usernameValidationError){
                    usernameValidationError = false
                }
            },
            label = {
                Text("username")
            },
            isError = usernameValidationError
        )
        if (usernameValidationError) {
            Text("username is blank,key in username", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if(passwordValidationError){
                    passwordValidationError = false
                }
            },
            label = {
                Text("password")
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "toggle visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordValidationError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        if (passwordValidationError) {
            Text("password is blank,key in password", color = MaterialTheme.colorScheme.error)

        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    authViewModel.loginUser(LoginData(username, password))
                } else {
                    usernameValidationError = username.isEmpty()
                    passwordValidationError = password.isEmpty()
                }
            },
            enabled = authState.value !is AuthState.Loading
        )
        {
            Text("Login")
        }
        when (val result = authState.value) {
            is AuthState.Error -> Text(text = result.message)
            is AuthState.Loading -> LoadingCircle()
            is AuthState.Success -> {}
            is AuthState.NotAuthenticated -> {}
            null -> {}
        }
        TextButton(onClick = {
            navigateToSignUp()
        }) {
            Text(text = "Don't have an account, sign up")
        }
        GoogleButton()
    }


}