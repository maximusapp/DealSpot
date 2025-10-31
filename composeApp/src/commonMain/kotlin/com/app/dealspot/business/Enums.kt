package com.app.dealspot.business

import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.female
import dealspot.composeapp.generated.resources.ic_female
import dealspot.composeapp.generated.resources.ic_male
import dealspot.composeapp.generated.resources.male
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class GenderType(val displayName: StringResource, val icon: DrawableResource) {
    MALE(Res.string.male, Res.drawable.ic_male),
    FEMALE(Res.string.female, Res.drawable.ic_female)
}

enum class VerificationCodeErrorType {
    ERROR_CODE_SHOULD_BE_6_DIGITS,
    CONFIRMATION_CODE_INCORRECT,
    CONFIRMATION_CODE_RESEND,
    NONE
}

enum class EmailPasswordDataValidationState {
    OK,
    EMAIL_INCORRECT,
    PASSWORD_LENGTH_INCORRECT
}

enum class ScreenType {
    CHATS, SETTINGS
}