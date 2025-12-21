package com.app.dealspot.di

import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.AppDataStoreManager
import com.app.dealspot.common.Context
import com.app.dealspot.data.AuthRepositoryImpl
import com.app.dealspot.data.DealRepositoryImpl
import com.app.dealspot.domain.use_cases.ConfirmForgotPasswordUseCase
import com.app.dealspot.domain.use_cases.EmailVerificationUseCase
import com.app.dealspot.domain.use_cases.ForgotPasswordUseCase
import com.app.dealspot.domain.use_cases.SignUpUseCase
import com.app.dealspot.domain.use_cases.LoginUseCase
import com.app.dealspot.domain.use_cases.deals.CreateDealUseCase
import com.app.dealspot.presentation.SharedViewModel
import com.app.dealspot.presentation.ui.SplashViewModel
import com.app.dealspot.presentation.ui.auth.email_verification.EmailVerificationScreenViewModel
import com.app.dealspot.presentation.ui.auth.forgot_password.ForgotPasswordViewModel
import com.app.dealspot.presentation.ui.auth.forgot_password.VerificationCodeViewModel
import com.app.dealspot.presentation.ui.auth.login.LoginViewModel
import com.app.dealspot.presentation.ui.auth.registration.RegistrationViewModel
import com.app.dealspot.presentation.ui.home.home.HomeScreenViewModel
import com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service.LookingForServiceViewModel
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
    single { ConfirmForgotPasswordUseCase(get()) }
    single { ForgotPasswordViewModel(get()) }
    single { VerificationCodeViewModel(get()) }

    /**CREATE_DEAL_USE-CASES*/
    single { CreateDealUseCase(get()) }

    /**PROFILE-USE-CASES*/
//    single { LogoutUseCase(get()) }
//    single { GetUserProfileDataUseCase(get()) }
//    single { RefreshAccessTokenUseCase(get()) }

    /** Repository */
    single { AuthRepositoryImpl() }
    single { DealRepositoryImpl() }
//    single { ProfileRepositoryImpl(get()) }
//
    single<AppDataStore> { AppDataStoreManager(context) }
    factory { SplashViewModel() }
    factory { SharedViewModel(get()) }
    factory { WelcomeScreenViewModel(get()) }
    factory { RegistrationViewModel(get(), get(), get()) }
    factory { EmailVerificationScreenViewModel(get()) }
    single { HomeScreenViewModel() }
    factory { LoginViewModel(get(), get(), get()) }
    factory { LookingForServiceViewModel(get()) }
//    factory { ProfileViewModel(get()) }
//    factory { SettingsViewModel(get()) }
//    factory { EditProfileViewModel(get(), get(), get()) }
//    factory { PaymentMethodViewModel() }
//    factory { NotificationsViewModel(get()) }
}