package com.example.foodium.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.foodium.FoodiumPreferencesStore
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.network.AuthTokens
import com.example.foodium.network.BackendApi
import com.example.foodium.network.UsernameAddResponse
import com.example.foodium.network.UsernameData
import retrofit2.HttpException

sealed interface AuthState {
    data class Success(val auth: AuthTokens) : AuthState
    data class Error(val message:String) : AuthState
    object Loading : AuthState
    object NotAuthenticated:AuthState
}
sealed interface UpdateUsernameUiState {
    data class Success(val auth: UsernameAddResponse) : UpdateUsernameUiState
    data class Error(val message:String) : UpdateUsernameUiState
    object Loading : UpdateUsernameUiState
    object NotAuthenticated:UpdateUsernameUiState
}



class AuthViewModel(
    private val backendApi: BackendApi,
    private val preferencesDataStore:FoodiumPreferencesStore
):ViewModel(){
    private var userAuthTokens:AuthTokens=AuthTokens("","")
    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState
    private val  _updateUsernameState =MutableLiveData<UpdateUsernameUiState>()
    val updateUsernameState:LiveData<UpdateUsernameUiState> = _updateUsernameState
    init {
        getAuthTokensFromServer()
    }
    fun registerUser(userData:RegisterData){
        viewModelScope.launch {
            _authState.value = try {
                val result = backendApi.retrofitService.registerUser(userData)
                preferencesDataStore.saveString("accessToken",result.accessToken)
                preferencesDataStore.saveString("refreshToken",result.refreshToken)
                userAuthTokens=result
                AuthState.Success(result)
            } catch (e: IOException) {
                Log.d("error response", e.toString())
                AuthState.Error(e.toString())
            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.d("error at Http exception",errorMessage)
                AuthState.Error(errorMessage)
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
                AuthState.Success(result)
            } catch (e: IOException) {
                Log.d("error response", e.toString())
                AuthState.Error(e.toString())
            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.d("error at Http exception",errorMessage)
                AuthState.Error(errorMessage)
            }
        }
    }
    fun addUsername(username:String){
        viewModelScope.launch {
            _updateUsernameState.value=try {
                val result= backendApi.retrofitService.addUsername(UsernameData(username,userAuthTokens.accessToken,userAuthTokens.refreshToken))
                if(result.newTokens!=null){
                    userAuthTokens=result.newTokens
                    updatePreferencesDataStore(result.newTokens.accessToken,result.newTokens.refreshToken)
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
    private fun getAuthTokensFromServer(){
        viewModelScope.launch {
            _authState.value=try {
                val accessToken=preferencesDataStore.getString("accessToken")
                val refreshToken=preferencesDataStore.getString("refreshToken")
                if(accessToken==null || refreshToken==null){
                    AuthState.NotAuthenticated
                }else{
                    val result=backendApi.retrofitService.getAuthTokens(AuthTokens(accessToken,refreshToken))
                    Log.d("protected route",result.toString())

                    if(result.newTokens!=null){
                        preferencesDataStore.saveString("accessToken",result.newTokens.accessToken)
                        preferencesDataStore.saveString("refreshToken",result.newTokens.refreshToken)
                        userAuthTokens=result.newTokens
                        AuthState.Success(result.newTokens)
                    }
                    else{
                        AuthState.Success(AuthTokens(accessToken,refreshToken))
                    }



                }


            }catch (e:HttpException){
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AuthState.Error(errorMessage)
            }catch (e:IOException){
                AuthState.Error(e.toString())
            }catch (e:Exception){
                println(e.toString())
                AuthState.Error(e.toString())
            }
        }
    }
    fun logout(){
        userAuthTokens=AuthTokens("","")
        _authState.value=AuthState.NotAuthenticated
    }
}