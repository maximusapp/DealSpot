package com.app.dealspot.domain.model

data class UserProfileEntity(
    val mFAOptions: UserProfileMFAOptionsEntity? = null,
    val preferredMfaSetting: String? = null,
    val attributes: List<UserAttributeEntity>? = null,
    val mFASettingList: List<String>? = null,
    val userName: String? = null,
    val error: Throwable? = null,
    val afterRefreshToken: Boolean = false
)