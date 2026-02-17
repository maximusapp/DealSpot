package com.app.dealspot.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.EmailPasswordDataValidationState
import com.app.dealspot.business.LoginState
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.domain.model.TokenResponse
import com.app.dealspot.domain.use_cases.ForgotPasswordUseCase
import com.app.dealspot.domain.use_cases.LoginUseCase
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.incorrect_email
import dealspot.composeapp.generated.resources.password_info
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LoginViewModel(
    private val dataStore: AppDataStore,
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    private var email: String = ""
    private var password: String = ""

    private val _emailConfirmationState: MutableStateFlow<String> = MutableStateFlow("")
    val emailConfirmationState = _emailConfirmationState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.None)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun login() {
        viewModelScope.launch {
            _loginState.update { LoginState.Loading }

            _loginState.update {
                when(dataValidationState()) {
                    EmailPasswordDataValidationState.OK -> {
                        loginUseCase.invoke(email = email, password = password)
                    }

                    EmailPasswordDataValidationState.EMAIL_INCORRECT  -> {
                        LoginState.Error(message = Res.string.incorrect_email)
                    }

                    EmailPasswordDataValidationState.PASSWORD_LENGTH_INCORRECT -> {
                        LoginState.Error(message = Res.string.password_info)
                    }
                }
            }
        }
    }

    fun checkEmailConfirmationState() {
        println("checkEmailConfirmationState")
        viewModelScope.launch {
            val emailThatNeedToVerify = dataStore.getString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY) ?: ""

            println("emailThatNeedToVerify: $emailThatNeedToVerify")
            if (emailThatNeedToVerify.isNotEmpty()) {
                _emailConfirmationState.value = emailThatNeedToVerify
            }
        }
    }

    fun loginAfterEmailVerified() {
        println("loginAfterEmailValidation")

        viewModelScope.launch {
            val email = dataStore.getString(key = DataStoreKeys.USER_EMAIL).orEmpty()
            val password = dataStore.getString(key = DataStoreKeys.USER_PASSWORD).orEmpty()
            println("loginAfterEmailValidation. Email: $email")
            println("loginAfterEmailValidation. Password: $password")

            val loginResponse = loginUseCase.invoke(email = email, password = password)

            _loginState.value = loginResponse
        }
    }

    fun saveUserCredentialsToDataStore(loginResponse: TokenResponse?) {
        println("saveUserCredentialsToDataStore.")
        println("loginResponse: $loginResponse")
        viewModelScope.launch {
            val loginResponseValue = Json.encodeToString(loginResponse)
            println("loginResponseValue: $loginResponseValue")

            dataStore.putString(key = DataStoreKeys.USER_EMAIL, value = email)
            dataStore.putString(key = DataStoreKeys.USER_PASSWORD, value = password)
            dataStore.putString(key = DataStoreKeys.TOKEN_USER_DATA, value = loginResponseValue)
            dataStore.putString(key = DataStoreKeys.IS_USER_LOGGED_IN, value = "1")

            println("Stored user token data: ${dataStore.getString(key = DataStoreKeys.TOKEN_USER_DATA)}")
        }
    }

    fun clearLoginState() {
        _loginState.value = LoginState.None
    }

    fun forgotPassword() {
        viewModelScope.launch {
            val email = dataStore.getString(key = DataStoreKeys.USER_EMAIL).orEmpty()
            forgotPasswordUseCase.invoke(email = email)
        }
    }

    private fun dataValidationState(): EmailPasswordDataValidationState {
        return if (!email.contains("@")) {
            EmailPasswordDataValidationState.EMAIL_INCORRECT
        } else if (password.length < 8) {
            EmailPasswordDataValidationState.PASSWORD_LENGTH_INCORRECT
        } else {
            EmailPasswordDataValidationState.OK
        }
    }
}


