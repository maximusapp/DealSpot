package com.app.dealspot.presentation.ui.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.GenderType
import com.app.dealspot.business.LoginState
import com.app.dealspot.business.RegistrationState
import com.app.dealspot.business.Step1
import com.app.dealspot.business.Step2
import com.app.dealspot.business.Step3
import com.app.dealspot.business.constants.DataStoreKeys
import com.app.dealspot.domain.model.TokenResponse
import com.app.dealspot.domain.use_cases.SignUpUseCase
import com.app.dealspot.domain.use_cases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class RegistrationViewModel(
    private val dataStore: AppDataStore,
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _registrationState: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState.None)
    val registrationState = _registrationState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.None)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _activeStep = MutableStateFlow(1)
    val activeStep: StateFlow<Int> = _activeStep

    private val _step1 = MutableStateFlow(Step1())
    val step1: StateFlow<Step1> = _step1

    private val _step2 = MutableStateFlow(Step2())
    val step2: StateFlow<Step2> = _step2

    private val _step3 = MutableStateFlow(Step3())
    val step3: StateFlow<Step3> = _step3

    init {
        restoreFromDataStore()
    }

    private fun restoreFromDataStore() {
        viewModelScope.launch {
            val savedStep = dataStore.getString(DataStoreKeys.REG_ACTIVE_STEP)?.toIntOrNull() ?: 0
            _activeStep.value = savedStep.coerceIn(1, 4)

            val gender = dataStore.getString(DataStoreKeys.REG_GENDER) ?: "-1".ifEmpty { "-1" }

            _step1.value = Step1(
                avatarUri = dataStore.getString(DataStoreKeys.REG_AVATAR_URI) ?: "",
                fullName = dataStore.getString(DataStoreKeys.REG_FULL_NAME) ?: "",
                age = dataStore.getString(DataStoreKeys.REG_AGE) ?: "",
                gender = if (gender.isBlank()) null else if (gender.toInt() == GenderType.MALE.ordinal) GenderType.MALE else GenderType.FEMALE
            )

            _step2.value = Step2(
                email = dataStore.getString(DataStoreKeys.REG_EMAIL) ?: "",
                phone = dataStore.getString(DataStoreKeys.REG_PHONE) ?: ""
            )

            _step3.value = Step3(
                password = dataStore.getString(DataStoreKeys.REG_PASSWORD) ?: "",
                confirmPassword = dataStore.getString(DataStoreKeys.REG_CONFIRM_PASSWORD) ?: ""
            )
        }
    }

    fun updateStep(step: Int) {
        val newStep = step.coerceIn(1, 4)
        _activeStep.value = newStep
        persistActiveStep(newStep)
    }

    fun nextStep() {
        val canProceed = when (_activeStep.value) {
            1 -> _step1.value.isValid
            2 -> _step2.value.isValid
            3 -> _step3.value.isValid
            else -> true // Step 4 (review step) - always valid
        }
        if (!canProceed) return
        updateStep(_activeStep.value + 1)
    }

    fun prevStep() { updateStep(_activeStep.value - 1) }

    fun completeRegistration() {
        println("RegistrationViewModel. completeRegistration()")
        // Clear registration data. Need call after success registration
        viewModelScope.launch {
            val signUpResponse = signUpUseCase.invoke(
                name = step1.value.fullName, email = step2.value.email,
                password = step3.value.password, age = step1.value.age,
                phoneNumber = step2.value.phone, gender = step1.value.gender?.ordinal ?: 0
            )

            println("Registration. Response: $signUpResponse")

            if (signUpResponse.error != null) {
                if (signUpResponse.error != null) {
                    _registrationState.value = RegistrationState.Error(message = signUpResponse.error)
                }
            } else if (signUpResponse.email.orEmpty().isNotEmpty() && signUpResponse.password.orEmpty().isNotEmpty()) {
                /* Need show verification dialog and store email and password to login after success verification */
                /* Set user as registered after success response */
                dataStore.putBoolean(key = DataStoreKeys.IS_USER_REGISTERED, value = true)
                /* Store user email and password */
                dataStore.putString(key = DataStoreKeys.EMAIL_THAT_NEED_VERIFY, value = signUpResponse.email.orEmpty())
                dataStore.putString(key = DataStoreKeys.USER_EMAIL, value = signUpResponse.email.orEmpty())
                dataStore.putString(key = DataStoreKeys.USER_PASSWORD, value = signUpResponse.password.orEmpty())
                dataStore.putString(key = DataStoreKeys.USER_SUB, value = signUpResponse.userSub.orEmpty())

                _registrationState.value = RegistrationState.Success(email = signUpResponse.email.orEmpty())
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

            dataStore.putString(key = DataStoreKeys.TOKEN_USER_DATA, value = loginResponseValue)
            dataStore.putString(key = DataStoreKeys.IS_USER_LOGGED_IN, value = "1")
        }
    }

    fun setAvatar(uri: String) {
        _step1.value = _step1.value.copy(avatarUri = uri)
        persistString(DataStoreKeys.REG_AVATAR_URI, uri)
    }

    fun setFirstName(value: String) {
        _step1.value = _step1.value.copy(fullName = value)
        persistString(DataStoreKeys.REG_FULL_NAME, value)
    }

    fun setAge(value: String) {
        if (value.all { it.isDigit() } || value.isBlank()) {
            _step1.value = _step1.value.copy(age = value)
            persistString(DataStoreKeys.REG_AGE, value)
        }
    }

    fun setGender(value: GenderType) {
        _step1.value = _step1.value.copy(gender = value)
        persistString(DataStoreKeys.REG_GENDER, value.ordinal.toString())
    }

    fun setEmail(value: String) {
        _step2.value = _step2.value.copy(email = value)
        persistString(DataStoreKeys.REG_EMAIL, value)
    }

    fun setPhone(value: String) {
        if (value.all { it.isDigit() } || value.startsWith("+") || value.isBlank()) {
            _step2.value = _step2.value.copy(phone = value)
            persistString(DataStoreKeys.REG_PHONE, value)
        }
    }

    fun setPassword(value: String) {
        _step3.value = _step3.value.copy(password = value)
        persistString(DataStoreKeys.REG_PASSWORD, value)
    }

    fun setConfirmPassword(value: String) {
        _step3.value = _step3.value.copy(confirmPassword = value)
        persistString(DataStoreKeys.REG_CONFIRM_PASSWORD, value)
    }

    private fun persistActiveStep(step: Int) { persistString(DataStoreKeys.REG_ACTIVE_STEP, step.toString()) }

    private fun persistString(key: String, value: String) {
        viewModelScope.launch { dataStore.putString(key, value) }
    }

    fun clearRegistrationData() {
        viewModelScope.launch {
            // Clear all registration data
            dataStore.putString(DataStoreKeys.REG_ACTIVE_STEP, "0")
            dataStore.putString(DataStoreKeys.REG_AVATAR_URI, "")
            dataStore.putString(DataStoreKeys.REG_FULL_NAME, "")
            dataStore.putString(DataStoreKeys.REG_AGE, "")
            dataStore.putString(DataStoreKeys.REG_GENDER, "")
            dataStore.putString(DataStoreKeys.REG_EMAIL, "")
            dataStore.putString(DataStoreKeys.REG_PHONE, "")
            dataStore.putString(DataStoreKeys.REG_PASSWORD, "")
            dataStore.putString(DataStoreKeys.REG_CONFIRM_PASSWORD, "")
        }
    }

    fun clearStepsInfo() {
        _step1.value = Step1()
        _step2.value = Step2()
        _step3.value = Step3()
    }

    fun clearRegistrationState() {
        _registrationState.value = RegistrationState.None
    }
}