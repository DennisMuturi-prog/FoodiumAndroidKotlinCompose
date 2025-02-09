package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.network.BackendApi
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.network.RegisterResponse
import retrofit2.HttpException

sealed interface RegisterUiState {
    data class Success(val photos: RegisterResponse) : RegisterUiState
    data class Error(val message:String) : RegisterUiState
    object Loading : RegisterUiState
}

class AuthViewModel:ViewModel(){
    private val _authResult = MutableLiveData<RegisterUiState>()
    val authResult : LiveData<RegisterUiState> = _authResult
    fun registerUser(userData:RegisterData){
        viewModelScope.launch {
            _authResult.value = try {
                val result = BackendApi.retrofitService.registerUser(userData)
                Log.d("register1 response",result.toString())
                RegisterUiState.Success(result)
            } catch (e: IOException) {
                Log.d("error response", e.toString())
                RegisterUiState.Error(e.toString())
            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.d("error at Http exception",errorMessage)
                RegisterUiState.Error(errorMessage)
            }
        }
    }
    fun loginUser(userData: LoginData){
        viewModelScope.launch {
            _authResult.value = try {
                val result = BackendApi.retrofitService.loginUser(userData)
                Log.d("register1 response",result.toString())
                RegisterUiState.Success(result)
            } catch (e: IOException) {
                Log.d("error response", e.toString())
                RegisterUiState.Error(e.toString())
            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.d("error at Http exception",errorMessage)
                RegisterUiState.Error(errorMessage)
            }
        }
    }

}