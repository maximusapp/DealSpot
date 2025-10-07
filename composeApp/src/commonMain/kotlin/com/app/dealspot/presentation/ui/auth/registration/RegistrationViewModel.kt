package com.app.dealspot.presentation.ui.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.GenderType
import com.app.dealspot.business.Step1
import com.app.dealspot.business.Step2
import com.app.dealspot.business.Step3
import com.app.dealspot.business.constants.DataStoreKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val dataStore: AppDataStore
) : ViewModel() {



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
            val savedStep = dataStore.getString(DataStoreKeys.REG_ACTIVE_STEP)?.toIntOrNull() ?: 1
            _activeStep.value = savedStep.coerceIn(1, 3)

            val gender = dataStore.getString(DataStoreKeys.REG_GENDER) ?: ""

            _step1.value = Step1(
                avatarUri = dataStore.getString(DataStoreKeys.REG_AVATAR_URI) ?: "",
                firstName = dataStore.getString(DataStoreKeys.REG_FIRST_NAME) ?: "",
                lastName = dataStore.getString(DataStoreKeys.REG_LAST_NAME) ?: "",
                age = dataStore.getString(DataStoreKeys.REG_AGE) ?: "",
                gender = if (gender.toInt() == GenderType.FEMALE.ordinal) GenderType.FEMALE else GenderType.MALE
            )

            _step2.value = Step2(
                email = dataStore.getString(DataStoreKeys.REG_EMAIL) ?: "",
                phone = dataStore.getString(DataStoreKeys.REG_PHONE) ?: ""
            )

            _step3.value = Step3(
                password = dataStore.getString(DataStoreKeys.REG_PASSWORD) ?: "",
                confirmPassword = dataStore.getString(DataStoreKeys.USER_PASSWORD) ?: ""
            )
        }
    }

    fun updateStep(step: Int) {
        val newStep = step.coerceIn(1, 3)
        _activeStep.value = newStep
        persistActiveStep(newStep)
    }

    fun nextStep() {
        val canProceed = when (_activeStep.value) {
            1 -> _step1.value.isValid
            2 -> _step2.value.isValid
            else -> _step3.value.isValid
        }
        if (!canProceed) return
        updateStep(_activeStep.value + 1)
    }

    fun prevStep() { updateStep(_activeStep.value - 1) }

    fun setAvatar(uri: String) {
        _step1.value = _step1.value.copy(avatarUri = uri)
        persistString(DataStoreKeys.REG_AVATAR_URI, uri)
    }

    fun setFirstName(value: String) {
        _step1.value = _step1.value.copy(firstName = value)
        persistString(DataStoreKeys.REG_FIRST_NAME, value)
    }

    fun setLastName(value: String) {
        _step1.value = _step1.value.copy(lastName = value)
        persistString(DataStoreKeys.REG_LAST_NAME, value)
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
        persistString(DataStoreKeys.USER_PASSWORD, value)
    }

    private fun persistActiveStep(step: Int) { persistString(DataStoreKeys.REG_ACTIVE_STEP, step.toString()) }

    private fun persistString(key: String, value: String) {
        viewModelScope.launch { dataStore.putString(key, value) }
    }
}