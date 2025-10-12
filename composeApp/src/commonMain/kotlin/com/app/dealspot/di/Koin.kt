package com.app.dealspot.di

import com.app.dealspot.business.AppDataStore
import com.app.dealspot.business.AppDataStoreManager
import com.app.dealspot.common.Context
import com.app.dealspot.presentation.SharedViewModel
import com.app.dealspot.presentation.ui.SplashViewModel
import com.app.dealspot.presentation.ui.auth.email_verification.EmailVerificationScreenViewModel
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
//    single { CheckIsAppOpenedFirstTimeUseCase(get()) }
//    single { UpdateFirstTimeAppOpenedUseCase(get()) }
//    single { EmailPasswordValidatorUseCase() }
//    single { LoginUseCase(get()) }
//    single { SignUpUseCase(get()) }
//    single { GetFlagByPhoneNumberUseCase() }
//    single { RegistrationDataValidatorUseCase() }
//    single { EmailVerificationUseCase(get(), get()) }

    /**PROFILE-USE-CASES*/
//    single { LogoutUseCase(get()) }
//    single { GetUserProfileDataUseCase(get()) }
//    single { RefreshAccessTokenUseCase(get()) }

    /** Repository */
//    single { AuthRepositoryImpl() }
//    single { ProfileRepositoryImpl(get()) }
//
    single<AppDataStore> { AppDataStoreManager(context) }
    factory { SplashViewModel() }
    factory { SharedViewModel(get()) }
    factory { WelcomeScreenViewModel(get()) }
    factory { RegistrationViewModel(get()) }
    factory { EmailVerificationScreenViewModel() }
    factory { HomeScreenViewModel() }
//    factory { LoginViewModel(get(), get(), get()) }
//    factory { RegisterViewModel(get(), get(), get(), get()) }
//    factory { EmailVerificationScreenViewModel(get()) }
//    factory { HomeScreenViewModel(get(), get(), get(), get()) }
//    factory { HomeViewModel(get(), get()) }
//    factory { AddressViewModel(get(), get()) }
//    factory { CategoriesViewModel(get()) }
//    factory { ProfileViewModel(get()) }
//    factory { SettingsViewModel(get()) }
//    factory { EditProfileViewModel(get(), get(), get()) }
//    factory { PaymentMethodViewModel() }
//    factory { NotificationsViewModel(get()) }
}