package com.app.dealspot.business

import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.female
import dealspot.composeapp.generated.resources.male
import dealspot.composeapp.generated.resources.none
import org.jetbrains.compose.resources.StringResource

enum class GenderType(val displayName: StringResource) {
    MALE(Res.string.male),
    FEMALE(Res.string.female)
}

enum class VerificationCodeErrorType {
    ERROR_CODE_SHOULD_BE_6_DIGITS,
    CONFIRMATION_CODE_INCORRECT,
    CONFIRMATION_CODE_RESEND,
    NONE
}