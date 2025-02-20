package com.example.foodium.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodium.network.HealthAttributesData
import com.example.foodium.network.LoginData
import com.example.foodium.ui.components.DietTypeSelection

@Composable
fun AddHealthAttributes(modifier: Modifier = Modifier,
                        authViewModel: AuthViewModel,
                        onSuccessAddHealthAttributes:()->Unit) {
    var dietType by remember {
        mutableStateOf("none")
    }
    var userWeight by remember {
        mutableStateOf("")
    }
    var userNoOfMealsADay by remember {
        mutableStateOf("")
    }
    val healthAttributesState=authViewModel.updateUserHealthAttrState.observeAsState()
    LaunchedEffect(healthAttributesState.value) {
        when(healthAttributesState.value){
            is AuthState.Success ->onSuccessAddHealthAttributes()
            else -> Unit
        }
    }
    Column(
        modifier=modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text=dietType)
        Text(text="Health Attributes", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value=userWeight,
            onValueChange = {userWeight=it},
            label = {
                Text("weight")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value=userNoOfMealsADay,
            onValueChange = {userNoOfMealsADay=it},
            label = {
                Text("no of meals a day")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DietTypeSelection (onDietTypeSelect = {dietType=it})
        Button(onClick = {
            val result=authViewModel.addUserHealthAttributes(HealthAttributesData(userWeight.toInt(),dietType,userNoOfMealsADay.toInt()))
            Log.d("register response", result.toString())
        }) {
            Text("Login")
        }
        when(val result=healthAttributesState.value){
            is AuthState.Error->Text(text=result.message)
            is AuthState.Loading->Text("loading")
            is AuthState.Success->{}
            is AuthState.NotAuthenticated->{}
            null->{}
        }
    }

}