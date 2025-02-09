package com.example.foodium.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.network.BackendApi
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.foodium.FoodiumPreferencesStore
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.network.RegisterResponse
import com.example.foodium.network.UsernameAddResponse
import com.example.foodium.network.UsernameData
import retrofit2.HttpException

import android.app.Application
import androidx.lifecycle.AndroidViewModel

sealed interface RegisterUiState {
    data class Success(val auth: RegisterResponse) : RegisterUiState
    data class Error(val message:String) : RegisterUiState
    object Loading : RegisterUiState
}
sealed interface UpdateUsernameUiState {
    data class Success(val usernameUpdate: UsernameAddResponse) : UpdateUsernameUiState
    data class Error(val message:String) : UpdateUsernameUiState
    object Loading : UpdateUsernameUiState
}

class AuthViewModel(application:Application):AndroidViewModel(application){
    private val preferencesDataStore by lazy { FoodiumPreferencesStore(application) }
    private val _authState = MutableLiveData<RegisterUiState>()
    val authState : LiveData<RegisterUiState> = _authState
    private val  _updateUsernameState =MutableLiveData<UpdateUsernameUiState>()
    val updateUsernameState:LiveData<UpdateUsernameUiState> = _updateUsernameState
    init {
        viewModelScope.launch {
            _authState.value=try {
                val accessToken=preferencesDataStore.getString("accessToken").toString()
                val refreshToken=preferencesDataStore.getString("refreshToken").toString()
                RegisterUiState.Success(RegisterResponse(accessToken,refreshToken))
            }catch(e:Exception){
                RegisterUiState.Error(e.toString())
            }
        }
    }
    fun registerUser(userData:RegisterData){
        viewModelScope.launch {
            _authState.value = try {
                val result = BackendApi.retrofitService.registerUser(userData)
                preferencesDataStore.saveString("accessToken",result.accessToken)
                preferencesDataStore.saveString("refreshToken",result.refreshToken)
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
            _authState.value = try {
                val result = BackendApi.retrofitService.loginUser(userData)
                preferencesDataStore.saveString("accessToken",result.accessToken)
                preferencesDataStore.saveString("refreshToken",result.refreshToken)
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
    fun addUsername(usernameData:UsernameData){
        viewModelScope.launch {
            _updateUsernameState.value=try {
                val result= BackendApi.retrofitService.addUsername(usernameData)
                if(result.newTokens!=null){
                    preferencesDataStore.saveString("accessToken",result.newTokens.accessToken)
                    preferencesDataStore.saveString("refreshToken",result.newTokens.refreshToken)
                    updateTokens(result.newTokens)
                }
                UpdateUsernameUiState.Success(result)
            }catch (e:IOException){
                UpdateUsernameUiState.Error(e.toString())

            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                UpdateUsernameUiState.Error(errorMessage)
            }
        }
    }
    private fun updateTokens(newTokens: RegisterResponse){
        _authState.value=RegisterUiState.Success(newTokens)
    }
    fun addTokensToStore(newTokens: RegisterResponse){
        viewModelScope.launch {
            preferencesDataStore.saveString("accessToken",newTokens.accessToken)
            preferencesDataStore.saveString("refreshToken",newTokens.refreshToken)
            updateTokens(newTokens)
        }
    }

}