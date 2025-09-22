package com.app.dealspot.di

import com.app.dealspot.common.Context
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
//    single<AppDataStore> { AppDataStoreManager(context) }
//    factory { BaseAuthViewModel() }
//    factory { SplashViewModel(get(), get(), get()) }
//    factory { SharedViewModel(get()) }
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
//    factory { MyCouponsViewModel() }
//    factory { MyOrdersViewModel(get()) }
//    factory { CheckoutViewModel(get(), get(), get()) }
//    factory { WishlistViewModel(get(), get()) }
//    factory { CartViewModel(get(), get(), get()) }
//    factory { DetailViewModel(get(), get(), get()) }
//    factory { SearchViewModel(get(), get()) }
//    single { WishListInteractor(get(), get()) }
//    single { BasketListInteractor(get(), get()) }
//    single { GetProfileInteractor(get(), get()) }
//    single { UpdateProfileInteractor(get(), get()) }
//    single { TokenManager(get(), get()) }
//    single { LogoutInteractor(get()) }
//    single { GetEmailFromCacheInteractor(get()) }
//    single { GetSearchFilterInteractor(get(), get()) }
//    single { SearchInteractor(get(), get()) }
//    single { AddCommentInteractor(get(), get()) }
//    single { BuyProductInteractor(get(), get()) }
//    single { CommentViewModel(get(), get() ) }
//    single { GetCommentsInteractor(get(), get()) }
//    single { GetAddressesInteractor(get(), get()) }
//    single { GetOrdersInteractor(get(), get()) }
//    single { GetNotificationsInteractor(get(), get()) }
//    single { AddAddressInteractor(get(), get()) }
//    single { AddBasketInteractor(get(), get()) }
//    single { DeleteBasketInteractor(get(), get()) }
//    single { LikeInteractor(get(), get()) }
//    single { LoginInteractor(get(), get()) }
//    single { RegisterInteractor(get(), get()) }
//    single { CheckTokenInteractor(get()) }
//    single { HomeInteractor(get(), get()) }
//    single { ProductInteractor(get(), get()) }
}