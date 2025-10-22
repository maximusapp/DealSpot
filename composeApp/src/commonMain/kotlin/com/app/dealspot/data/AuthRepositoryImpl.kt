package com.app.dealspot.data

import com.app.dealspot.business.LoginState
import com.app.dealspot.business.ResendVerificationCodeState
import com.app.dealspot.business.VerificationEmailState
import com.app.dealspot.common.AWSConfig.CLIENT_ID
import com.app.dealspot.common.AWSConfig.REGION
import com.app.dealspot.data.model.Error
import com.app.dealspot.data.model.LoginResponse
import com.app.dealspot.data.model.SignUpResponse
import com.app.dealspot.data.model.TokenResponse
import com.dealspot.network.IdentityProviderClient
import com.dealspot.network.core_cognito.IdentityProviderException
import com.dealspot.network.core_cognito.UserAttribute
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.something_went_wrong_try_again
import dealspot.composeapp.generated.resources.user_already_exists
import org.jetbrains.compose.resources.StringResource

class AuthRepositoryImpl() {
    private val provider = IdentityProviderClient(REGION, CLIENT_ID)

    suspend fun signUp(name: String, email: String, password: String, age: String = "", phoneNumber: String = "", gender: Int = 0): SignUpResponse {
        println("AuthRepositoryImpl. signUp.")
        println("Params: name: $name, email: $email, age: $age, phoneNumber: $phoneNumber, gender: $gender")

        val result = SignUpResponse()
        val attributes = listOf(
            UserAttribute(Name = "custom:age", Value = age),
            UserAttribute(Name = "phone_number", Value = phoneNumber),
            UserAttribute(Name = "gender", Value = gender.toString()),
            UserAttribute(Name = "name", Value = name)
        )

        println("AuthRepositoryImpl. signUp. attributes: $attributes")

        provider.signUp(username = email, password = password, attributes = attributes).fold(
            onSuccess = {
                println("Sign up. Success. Response: $it")

                result.apply {
                    this.email = email
                    this.password = password
                    this.userSub = it.UserSub
                }
            },
            onFailure = {
                println("Sign up. Failure. Response: $it")
                result.error = getRegistrationErrorMessage(it)
            }
        )

        return result
    }

    suspend fun login(email: String, password: String): LoginState {
        var result: LoginState = LoginState.None

        provider.signIn(username = email, password = password).fold(
            onSuccess = {
                println("Log in. Success")
                println("Log in. accessToken: ${it.AuthenticationResult?.AccessToken.orEmpty()}")
                println("Log in. ExpiresIn: ${it.AuthenticationResult?.ExpiresIn}")
                println("Log in. IdToken: ${it.AuthenticationResult?.IdToken}")
                println("Log in. RefreshToken: ${it.AuthenticationResult?.RefreshToken}")
                println("Log in. TokenType: ${it.AuthenticationResult?.TokenType}")
                println("Log in. ==============")
                println("Log in. ChallengeParameters: ${it.ChallengeParameters}")
                println("Log in. ChallengeName: ${it.ChallengeName}")
                println("Log in. Session: ${it.Session}")

                val tokenResponse = TokenResponse(
                    accessToken = it.AuthenticationResult?.AccessToken.orEmpty(),
                    expiresIn = it.AuthenticationResult?.ExpiresIn ?: 0,
                    idToken = it.AuthenticationResult?.IdToken.orEmpty(),
                    refreshToken = it.AuthenticationResult?.RefreshToken.orEmpty(),
                    tokenType = it.AuthenticationResult?.TokenType.orEmpty()
                )
                result = LoginState.Success(response = LoginResponse(tokenResponse = tokenResponse, error = null))
            },
            onFailure = {
                println("Log in. Failure. Response: $it")
                result = LoginState.Error(type = it)
            }
        )

        return result
    }

    suspend fun verifyEmail(email: String, code: String): VerificationEmailState {
        var result: VerificationEmailState = VerificationEmailState.None

        provider.confirmSignUp(username = email, confirmationCode = code).fold(
            onSuccess = {
                println("verifyEmail. Success.")
                result = VerificationEmailState.Success
            },

            onFailure = {
                println("verifyEmail. Failure. Error: ${it.message} \n Cause: ${it.cause}")
                result = VerificationEmailState.Error(message = it.message.orEmpty(), cause = it.cause?.message.orEmpty())
            }
        )

        return result
    }

    suspend fun resendVerificationCode(email: String): ResendVerificationCodeState {
        var result: ResendVerificationCodeState = ResendVerificationCodeState.None

        provider.resendConfirmationCode(username = email).fold(
            onSuccess = {
                println("resendVerificationCode. Success.")
                result = ResendVerificationCodeState.Success
            },

            onFailure = {
                println("resendVerificationCode. Failure. Error: ${it.message} \n Cause: ${it.cause}")
                result = ResendVerificationCodeState.Error(message = it.message.orEmpty(), cause = it.cause?.message.orEmpty())
            }
        )

        return result
    }

    private fun getRegistrationErrorMessage(errorType: Throwable): StringResource {
        return when(errorType) {
            is IdentityProviderException.UsernameExistsException -> {
                println("Sign up. Error type: UsernameExistsException")
                Res.string.user_already_exists
            }

            else -> Res.string.something_went_wrong_try_again
        }
    }

}