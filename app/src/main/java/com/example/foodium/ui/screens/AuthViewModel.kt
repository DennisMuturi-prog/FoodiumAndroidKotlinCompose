package com.example.foodium.ui.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodium.network.HealthAttributesData
import com.example.foodium.network.LoginData
import com.example.foodium.network.RegisterData
import com.example.foodium.repository.Repository
import com.example.foodium.ui.components.snackbarconfig.SnackbarAction
import com.example.foodium.ui.components.snackbarconfig.SnackbarController
import com.example.foodium.ui.components.snackbarconfig.SnackbarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException

sealed interface AuthState {
    data object Success : AuthState
    data class Error(val message: String) : AuthState
    data object Loading : AuthState
    data object NotAuthenticated : AuthState
}


class AuthViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private val _landingAuthState = MutableLiveData<AuthState>()
    val landingAuthState: LiveData<AuthState> = _landingAuthState
    private val _updateUsernameState = MutableLiveData<AuthState>()
    val updateUsernameState: LiveData<AuthState> = _updateUsernameState
    private val _updateUserHealthAttrState = MutableLiveData<AuthState>()
    val updateUserHealthAttrState: LiveData<AuthState> = _updateUserHealthAttrState

    init {
        getAuthTokensFromServer()
    }
    fun registerUser(userData: RegisterData) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            _authState.value = try {
                repository.registerUser(userData)
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = "sign up successful",
                    )
                )
                AuthState.Success
            } catch (e: IOException) {
                AuthState.Error(e.toString())
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AuthState.Error(errorMessage)
            }
        }
    }

    fun loginUser(userData: LoginData) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            _authState.value = try {
                repository.loginUser(userData)
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = "login successful",
                    )
                )
                AuthState.Success
            } catch (e: IOException) {
                AuthState.Error(e.toString())
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AuthState.Error(errorMessage)
            }
        }
    }

    fun addUsername(username: String) {
        _updateUsernameState.value = AuthState.Loading
        viewModelScope.launch {
            _updateUsernameState.value = try {
                repository.addUsername(username)
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = "username added successfully",
                    )
                )
                AuthState.Success
            } catch (e: IOException) {
                AuthState.Error(e.toString())

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AuthState.Error(errorMessage)
            }
        }
    }

    fun updatePreferencesDataStore(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            repository.updatePreferencesDataStore(accessToken, refreshToken)
        }
    }

    private fun getAuthTokensFromServer() {
        _landingAuthState.value = AuthState.Loading
        viewModelScope.launch{
            _landingAuthState.value = try {
                val result = repository.getAuthTokensFromServer()
                if (result == "authenticated") {
                    AuthState.Success
                } else {
                    AuthState.NotAuthenticated
                }
            } catch (e: SocketTimeoutException) {
                // Handle timeout (e.g., show a message to the user)
                SnackbarController.sendEvent(
                    event=SnackbarEvent(
                        message = "connection timed out",
                        action = SnackbarAction(
                            name = "retry",
                            action = {getAuthTokensFromServer()}
                        )
                    )
                )
                AuthState.Error(e.toString())
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AuthState.Error(errorMessage)
            } catch (e: IOException) {
                AuthState.Error(e.toString())
            } catch (e: Exception) {
                AuthState.Error(e.toString())
            }
        }
    }

    fun logout() {
        _authState.value = AuthState.NotAuthenticated
        _landingAuthState.value =AuthState.NotAuthenticated
        viewModelScope.launch {
            repository.logout()
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = "you logged out",
                )
            )
        }

    }

    fun addUserHealthAttributes(healthData: HealthAttributesData) {
        _updateUserHealthAttrState.value = AuthState.Loading
        viewModelScope.launch {
            _updateUserHealthAttrState.value = try {
                repository.addUserHealthAttributes(healthData)
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = "added user health attributes successful",
                    )
                )
                AuthState.Success
            } catch (e: IOException) {
                AuthState.Error(e.toString())

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AuthState.Error(errorMessage)
            }
        }
    }
}