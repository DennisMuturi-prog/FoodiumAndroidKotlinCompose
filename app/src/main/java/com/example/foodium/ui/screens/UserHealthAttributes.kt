package com.example.foodium.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodium.network.HealthAttributesData
import com.example.foodium.network.LoginData
import com.example.foodium.ui.components.DietTypeSelection
import com.example.foodium.ui.components.LoadingCircle
import com.example.foodium.utils.validateNofMeals
import com.example.foodium.utils.validateWeight

@Composable
fun AddHealthAttributes(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onSuccessAddHealthAttributes: () -> Unit
) {
    var dietType by remember {
        mutableStateOf("none")
    }
    var userWeight by remember {
        mutableStateOf("")
    }
    var userNoOfMealsADay by remember {
        mutableStateOf("")
    }
    var userWeightValidationError by remember {
        mutableStateOf("")
    }
    var userNoOfMealsValidationError by remember {
        mutableStateOf("")
    }
    var submitWasClicked by remember {
        mutableStateOf(false)
    }
    val healthAttributesState = authViewModel.updateUserHealthAttrState.observeAsState()
    LaunchedEffect(healthAttributesState.value) {
        when (healthAttributesState.value) {
            is AuthState.Success -> onSuccessAddHealthAttributes()
            else -> Unit
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = dietType)
        Text(text = "Health Attributes", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userWeight,
            onValueChange = {
                userWeight = it
                if(submitWasClicked){
                    userWeightValidationError= validateWeight(userWeight)
                }
            },
            label = {
                Text("weight(kg)")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = userWeightValidationError.isNotEmpty()
        )
        if (userWeightValidationError.isNotEmpty()) {
            Text(userWeightValidationError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = userNoOfMealsADay,
            onValueChange = {
                userNoOfMealsADay = it
                if (submitWasClicked) {
                    userNoOfMealsValidationError = validateNofMeals(userNoOfMealsADay)
                }
            },
            label = {
                Text("no of meals a day")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = userNoOfMealsValidationError.isNotEmpty()
        )
        if (userNoOfMealsValidationError.isNotEmpty()) {
            Text(userNoOfMealsValidationError, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        DietTypeSelection(onDietTypeSelect = { dietType = it })
        Button(
            onClick = {
                submitWasClicked=true
                userWeightValidationError = validateWeight(userWeight)
                userNoOfMealsValidationError = validateNofMeals(userNoOfMealsADay)
                if (userWeightValidationError.isEmpty() && userNoOfMealsValidationError.isEmpty()) {
                    val result = authViewModel.addUserHealthAttributes(
                        HealthAttributesData(
                            userWeight.toInt(),
                            dietType,
                            userNoOfMealsADay.toInt()
                        )
                    )
                    Log.d("register response", result.toString())
                }
            },
            enabled = healthAttributesState.value !is AuthState.Loading
        ) {
            Text("Login")
        }
        when (val result = healthAttributesState.value) {
            is AuthState.Error -> Text(text = result.message)
            is AuthState.Loading -> LoadingCircle()
            is AuthState.Success -> {}
            is AuthState.NotAuthenticated -> {}
            null -> {}
        }
    }

}