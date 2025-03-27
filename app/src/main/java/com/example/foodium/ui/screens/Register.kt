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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodium.network.RegisterData
import com.example.foodium.ui.components.GoogleButton
import com.example.foodium.ui.components.LoadingCircle
import com.example.foodium.utils.validateConfirmPassword
import com.example.foodium.utils.validateEmail
import com.example.foodium.utils.validatePassword
import com.example.foodium.utils.validateUsername

@Composable
fun Register(
    modifier: Modifier,
    authViewModel: AuthViewModel,
    onSuccessAuthentication: () -> Unit,
    navigateToLogin: () -> Unit
) {
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var usernameValidationError by remember {
        mutableStateOf("")
    }
    var emailValidationError by remember {
        mutableStateOf("")
    }
    var passwordValidationError by remember {
        mutableStateOf("")
    }
    var confirmPasswordError by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var submitWasClicked by remember {
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
        Text(text = "Sign Up", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                if (submitWasClicked) {
                    usernameValidationError = validateUsername(username)
                }
            },
            label = {
                Text("username")
            },
            isError = usernameValidationError.isNotEmpty(),
        )
        if (usernameValidationError.isNotEmpty()) {
            Text(usernameValidationError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (submitWasClicked) {
                    emailValidationError = validateEmail(email)
                }
            },
            label = {
                Text("email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = emailValidationError.isNotEmpty()
        )
        if (emailValidationError.isNotEmpty()) {
            Text(emailValidationError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (submitWasClicked) {
                    passwordValidationError = validatePassword(password)
                }
            },
            label = {
                Text("password")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = passwordValidationError.isNotEmpty()
        )
        if (passwordValidationError.isNotEmpty()) {
            Text(passwordValidationError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                if(submitWasClicked){
                    confirmPasswordError= validateConfirmPassword(password,confirmPassword)
                }
            },
            label = {
                Text("confirm password")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
            isError = confirmPasswordError.isNotEmpty(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )

        )
        if (confirmPasswordError.isNotEmpty()) {
            Text(confirmPasswordError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                submitWasClicked = true
                usernameValidationError = validateUsername(username)
                emailValidationError = validateEmail(email)
                passwordValidationError = validatePassword(password)
                confirmPasswordError = validateConfirmPassword(password, confirmPassword)
                if (usernameValidationError.isEmpty() && emailValidationError.isEmpty() && passwordValidationError.isEmpty() && confirmPasswordError.isEmpty()) {
                    val result = authViewModel.registerUser(RegisterData(username, email, password))
                    Log.d("register response", result.toString())
                }


            },
            enabled = authState.value !is AuthState.Loading
        )
        {
            Text("register")
        }
        when (val result = authState.value) {
            is AuthState.Error -> Text(text = result.message)
            is AuthState.Loading -> LoadingCircle()
            is AuthState.Success -> {}
            is AuthState.NotAuthenticated -> {}
            null -> {}
        }
        TextButton(onClick = {
            navigateToLogin()
        }) {
            Text(text = "Already have an account, Login")
        }
        GoogleButton()
    }


}