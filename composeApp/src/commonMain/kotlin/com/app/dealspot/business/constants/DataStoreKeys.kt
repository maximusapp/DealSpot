package com.app.dealspot.business.constants

object DataStoreKeys {
    const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
    const val IS_APP_FIRST_TIME_OPENED = "IS_APP_FIRST_TIME_OPENED"
    const val IS_EMAIL_VERIFIED = "IS_EMAIL_VERIFIED"
    const val IS_USER_REGISTERED = "IS_USER_REGISTERED"
    const val EMAIL_THAT_NEED_VERIFY = "EMAIL_THAT_NEED_VERIFY"
    const val TOKEN_USER_DATA = "TOKEN_USER_DATA"
    const val ACCESS_TOKEN_LAST_UPDATED = "ACCESS_TOKEN_LAST_UPDATED"
    const val USER_EMAIL = "USER_EMAIL"
    const val USER_PASSWORD = "USER_PASSWORD"
    const val USER_SUB = "USER_SUB"

    // Registration flow persistence
    const val REG_ACTIVE_STEP = "REG_ACTIVE_STEP" // values: 1,2,3
    const val REG_AVATAR_URI = "REG_AVATAR_URI"
    const val REG_FULL_NAME = "REG_FULL_NAME"
    const val REG_AGE = "REG_AGE"
    const val REG_GENDER = "REG_GENDER" // "male" | "female" | ""
    const val REG_EMAIL = "REG_EMAIL"
    const val REG_PHONE = "REG_PHONE"
    const val REG_PASSWORD = "REG_PASSWORD"
    const val REG_CONFIRM_PASSWORD = "REG_CONFIRM_PASSWORD"
}