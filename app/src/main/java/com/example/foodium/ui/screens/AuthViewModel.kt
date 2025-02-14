package com.example.foodium.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.foodium.BackendApi
import com.example.foodium.FoodiumPreferencesStore
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.UsernameAddResponse
import com.example.foodium.network.UsernameData
import retrofit2.HttpException

sealed interface RegisterUiState {
    data class Success(val auth: AuthTokens) : RegisterUiState
    data class Error(val message:String) : RegisterUiState
    object Loading : RegisterUiState
    object NotAuthenticated:RegisterUiState
}
sealed interface UpdateUsernameUiState {
    data class Success(val usernameUpdate: UsernameAddResponse) : UpdateUsernameUiState
    data class Error(val message:String) : UpdateUsernameUiState
    object Loading : UpdateUsernameUiState
}


class AuthViewModel(
    private val backendApi: BackendApi,
    private val preferencesDataStore:FoodiumPreferencesStore
):ViewModel(){
    private var userAuthTokens:AuthTokens=AuthTokens("","")
    private val _authState = MutableLiveData<RegisterUiState>()
    val authState : LiveData<RegisterUiState> = _authState
    private val  _updateUsernameState =MutableLiveData<UpdateUsernameUiState>()
    val updateUsernameState:LiveData<UpdateUsernameUiState> = _updateUsernameState
    init {
        viewModelScope.launch {
            val accessToken=preferencesDataStore.getString("accessToken")
            val refreshToken=preferencesDataStore.getString("refreshToken")
            if(accessToken==null || refreshToken==null){
                _authState.value=RegisterUiState.NotAuthenticated
            }else{
                getAuthTokensFromServer(AuthTokens(accessToken,refreshToken))
            }
        }
    }
    fun registerUser(userData:RegisterData){
        viewModelScope.launch {
            _authState.value = try {
                val result = backendApi.retrofitService.registerUser(userData)
                preferencesDataStore.saveString("accessToken",result.accessToken)
                preferencesDataStore.saveString("refreshToken",result.refreshToken)
                userAuthTokens=result
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
                val result = backendApi.retrofitService.loginUser(userData)
                preferencesDataStore.saveString("accessToken",result.accessToken)
                preferencesDataStore.saveString("refreshToken",result.refreshToken)
                userAuthTokens=result
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
    fun addUsername(username:String){
        viewModelScope.launch {
            _updateUsernameState.value=try {
                val result= backendApi.retrofitService.addUsername(UsernameData(username,userAuthTokens.accessToken,userAuthTokens.refreshToken))
                if(result.newTokens!=null){
                    userAuthTokens=result.newTokens
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
    fun updatePreferencesDataStore(accessToken:String,refreshToken:String){
        viewModelScope.launch{
            preferencesDataStore.saveString("accessToken",accessToken)
            preferencesDataStore.saveString("refreshToken",refreshToken)
        }
    }
    fun updateUserAuthTokens(tokens:AuthTokens){
        userAuthTokens=tokens
    }
    private fun getAuthTokensFromServer(tokens: AuthTokens){
        viewModelScope.launch {
            _authState.value=try {
                val result=backendApi.retrofitService.getAuthTokens(tokens)
                Log.d("protected route",result.toString())

                if(result.newTokens!=null){
                    preferencesDataStore.saveString("accessToken",result.newTokens.accessToken)
                    preferencesDataStore.saveString("refreshToken",result.newTokens.refreshToken)
                    userAuthTokens=result.newTokens
                    RegisterUiState.Success(result.newTokens)
                }
                else{
                    RegisterUiState.Success(tokens)
                }

            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                RegisterUiState.Error(errorMessage)
            }catch (e:IOException){
                RegisterUiState.Error(e.toString())
            }catch (e:Exception){
                println(e.toString())
                RegisterUiState.Error(e.toString())
            }
        }
    }
    fun logout(){
        userAuthTokens=AuthTokens("","")
        _authState.value=RegisterUiState.NotAuthenticated
    }
}