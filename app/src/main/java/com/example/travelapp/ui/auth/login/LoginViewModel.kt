package com.example.travelapp.ui.auth.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelapp.core.network.ApiResult
import com.example.travelapp.core.network.safeApiCall
import com.example.travelapp.data.remote.api.TravelApiService
import com.example.travelapp.data.remote.dto.auth.LoginRequest
import com.example.travelapp.data.remote.dto.auth.ResetPasswordRequest
import com.example.travelapp.di.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(val travelApiService: TravelApiService, val session: Session) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginEvent>(LoginEvent.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<LoginNavigation>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _forgotPasswordState = MutableStateFlow<ForgotPasswordState>(ForgotPasswordState.Idle)
    val forgotState = _forgotPasswordState.asStateFlow()


    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }


    fun onLoginButtonClick() {
        viewModelScope.launch {
            _uiState.emit(LoginEvent.Loading)
            val response = safeApiCall {
                travelApiService.login(
                    LoginRequest(
                        email = _email.value,
                        password = _password.value
                    )
                )
            }
            when (response) {
                is ApiResult.Success -> {
                    _uiState.value = LoginEvent.Success
                    session.storeToken(response.data.data.token)
                    session.storeUserId(response.data.data.user.id)
                    _navigationEvent.emit(LoginNavigation.NavigationToHome)
                }

                is ApiResult.Error -> {
                    _uiState.emit(
                        LoginEvent.Error(
                            response.message
                        )
                    )
                }

                is ApiResult.Exception -> {
                    _uiState.emit(
                        LoginEvent.Error(
                            response.message
                        )
                    )
                }
            }

        }

    }

    fun onDontHaveAccountButtonClick() {
        viewModelScope.launch {
            _navigationEvent.emit(LoginNavigation.NavigationToSignUp)
        }
    }

    fun onForgetPasswordButtonClick(email:String) {
        if(email.isBlank()){
            _forgotPasswordState.value=ForgotPasswordState.Error("⚠\uFE0F Please enter a new password")
            return
        }
        viewModelScope.launch {
            _forgotPasswordState.value=ForgotPasswordState.Loading
            val response = safeApiCall {
                travelApiService.resetPassword(
                    ResetPasswordRequest(
                        email = email
                    )
                )
            }
            when (response) {
                is ApiResult.Success -> {
                    _forgotPasswordState.value =
                        ForgotPasswordState.Success("Password reset link sent to your email")
                }
                is ApiResult.Error -> {
                    _forgotPasswordState.value =
                        ForgotPasswordState.Error(response.message)
                }
                else -> {
                    _forgotPasswordState.value =
                        ForgotPasswordState.Error("Something went wrong")
                }
            }
        }
    }


    fun clearError(){
        _uiState.value=LoginEvent.Nothing
    }

    fun clearForgetPasswordState(){
        _forgotPasswordState.value=ForgotPasswordState.Idle
    }


    sealed class LoginNavigation {
        object NavigationToHome : LoginNavigation()
        object NavigationToSignUp : LoginNavigation()
    }

    sealed class ForgotPasswordState {
        object Idle : ForgotPasswordState()
        object Loading : ForgotPasswordState()
        data class Success(val message: String) : ForgotPasswordState()
        data class Error(val message: String) : ForgotPasswordState()
    }

    sealed class LoginEvent {
        object Nothing : LoginEvent()
        object Success : LoginEvent()
        data class Error(val message: String) : LoginEvent()
        object Loading : LoginEvent()
    }


}