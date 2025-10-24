package com.app.dealspot.di

import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.AppDataStoreManager
import com.app.dealspot.common.Context
import com.app.dealspot.data.AuthRepositoryImpl
import com.app.dealspot.domain.use_cases.EmailVerificationUseCase
import com.app.dealspot.domain.use_cases.ForgotPasswordUseCase
import com.app.dealspot.domain.use_cases.SignUpUseCase
import com.app.dealspot.domain.use_cases.LoginUseCase
import com.app.dealspot.presentation.SharedViewModel
import com.app.dealspot.presentation.ui.SplashViewModel
import com.app.dealspot.presentation.ui.auth.email_verification.EmailVerificationScreenViewModel
import com.app.dealspot.presentation.ui.auth.login.LoginViewModel
import com.app.dealspot.presentation.ui.auth.registration.RegistrationViewModel
import com.app.dealspot.presentation.ui.home.HomeScreenViewModel
import com.app.dealspot.presentation.ui.welcome.WelcomeScreenViewModel
import kotlinx.serialization.json.Json
import org.koin.dsl.module


fun appModule(context: Context) = module {
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
//    single {
//        KtorHttpClient.httpClient(get())
//    }
//    single<SplashService> { SplashServiceImpl(get()) }
//    single<MainService> { MainServiceImpl(get()) }
    /**Use-cases*/
    /**AUTH-USE-CASES*/
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { EmailVerificationUseCase(get(), get()) }
    single { ForgotPasswordUseCase(get()) }

    /**PROFILE-USE-CASES*/
//    single { LogoutUseCase(get()) }
//    single { GetUserProfileDataUseCase(get()) }
//    single { RefreshAccessTokenUseCase(get()) }

    /** Repository */
    single { AuthRepositoryImpl() }
//    single { ProfileRepositoryImpl(get()) }
//
    single<AppDataStore> { AppDataStoreManager(context) }
    factory { SplashViewModel() }
    factory { SharedViewModel(get()) }
    factory { WelcomeScreenViewModel(get()) }
    factory { RegistrationViewModel(get(), get(), get()) }
    factory { EmailVerificationScreenViewModel(get()) }
    factory { HomeScreenViewModel() }
    factory { LoginViewModel(get(), get(), get()) }
//    factory { ProfileViewModel(get()) }
//    factory { SettingsViewModel(get()) }
//    factory { EditProfileViewModel(get(), get(), get()) }
//    factory { PaymentMethodViewModel() }
//    factory { NotificationsViewModel(get()) }
}