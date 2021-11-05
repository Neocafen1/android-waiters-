package com.example.neocafewaiterapplication.application

import android.app.Application
import com.example.neocafewaiterapplication.bottom_navigation.menu.all_product.AllProductViewModel
import com.example.neocafewaiterapplication.bottom_navigation.new_order.new_order_products.NewOrderProductsViewModel
import com.example.neocafewaiterapplication.koin.retrofitModule
import com.example.neocafewaiterapplication.splash.RegistrationViewModel
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            modules(listOf(myModule, retrofitModule))
        }

    }
}

val myModule = module {
    viewModel { AllProductViewModel(get()) }
    viewModel { NewOrderProductsViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
}