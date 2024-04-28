package com.example.foodiesapp.di

import android.content.SharedPreferences
import com.example.foodiesapp.data.datasource.auth.AuthDataSource
import com.example.foodiesapp.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodiesapp.data.datasource.cart.CartDataSource
import com.example.foodiesapp.data.datasource.cart.CartDatabaseDataSource
import com.example.foodiesapp.data.datasource.category.CategoryApiDataSource
import com.example.foodiesapp.data.datasource.category.CategoryDataSource
import com.example.foodiesapp.data.datasource.menu.MenuApiDataSource
import com.example.foodiesapp.data.datasource.menu.MenuDataSource
import com.example.foodiesapp.data.datasource.user.UserDataSource
import com.example.foodiesapp.data.datasource.user.UserDataSourceImpl
import com.example.foodiesapp.data.repository.CartRepository
import com.example.foodiesapp.data.repository.CartRepositoryImpl
import com.example.foodiesapp.data.repository.CategoryRepository
import com.example.foodiesapp.data.repository.CategoryRepositoryImpl
import com.example.foodiesapp.data.repository.MenuRepository
import com.example.foodiesapp.data.repository.MenuRepositoryImpl
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.data.repository.UserRepositoryImpl
import com.example.foodiesapp.data.source.local.database.AppDatabase
import com.example.foodiesapp.data.source.local.database.dao.CartDao
import com.example.foodiesapp.data.source.local.pref.UserPreference
import com.example.foodiesapp.data.source.local.pref.UserPreferenceImpl
import com.example.foodiesapp.data.source.network.firebase.FirebaseService
import com.example.foodiesapp.data.source.network.firebase.FirebaseServiceImpl
import com.example.foodiesapp.data.source.network.service.FoodiesApiService
import com.example.foodiesapp.presentation.cart.CartViewModel
import com.example.foodiesapp.presentation.checkout.CheckoutViewModel
import com.example.foodiesapp.presentation.detailfood.DetailFoodViewModel
import com.example.foodiesapp.presentation.home.HomeViewModel
import com.example.foodiesapp.presentation.login.LoginViewModel
import com.example.foodiesapp.presentation.main.MainViewModel
import com.example.foodiesapp.presentation.profile.ProfileViewModel
import com.example.foodiesapp.presentation.register.RegisterViewModel
import com.example.foodiesapp.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    private val networkModule = module {
        single<FoodiesApiService> { FoodiesApiService.invoke() }
    }

    private val firebaseModule = module {
        single <FirebaseService> { FirebaseServiceImpl() }
    }
    private val localModule = module {
        single <AppDatabase> { AppDatabase.createInstance(androidContext()) }
        single <CartDao>{ get<AppDatabase>().cartDao() }
        single <SharedPreferences> { SharedPreferenceUtils.createPreference(
            androidContext(),
            UserPreferenceImpl.PREF_NAME)
        }
        single <UserPreference> { UserPreferenceImpl(get()) }
    }

    private val datasource = module {
        single <CartDataSource> { CartDatabaseDataSource(get()) }
        single <CategoryDataSource> { CategoryApiDataSource(get()) }
        single <MenuDataSource> { MenuApiDataSource(get()) }
        single <UserDataSource> { UserDataSourceImpl(get()) }
        single <AuthDataSource>{ FirebaseAuthDataSource (get()) }
    }
    private val repository = module {
        single <CartRepository> { CartRepositoryImpl(get()) }
        single <CategoryRepository> { CategoryRepositoryImpl(get()) }
        single <MenuRepository> { MenuRepositoryImpl(get()) }
        single <UserRepository> { UserRepositoryImpl(get())}
    }

    private val viewModelModule = module {
        viewModel {params ->
            DetailFoodViewModel(
                extras = params.get(),
                cartRepository = get()
            )

        }
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::ProfileViewModel)
        viewModel { MainViewModel(get()) }

    } 
    val modules = listOf (
        networkModule,
        localModule,
        datasource,
        repository,
        firebaseModule,
        viewModelModule
    )
}