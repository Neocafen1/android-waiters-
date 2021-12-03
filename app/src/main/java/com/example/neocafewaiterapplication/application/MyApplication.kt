package com.example.neocafewaiterapplication.application

import android.app.Application
import android.content.Context
import com.example.neocafeteae1prototype.data.local.LocalDatabase
import com.example.neocafewaiterapplication.viewModel.menu_vm.MenuViewModel
import com.example.neocafewaiterapplication.viewModel.menu_vm.AllProductViewModel
import com.example.neocafewaiterapplication.viewModel.new_order.NewOrderViewModel
import com.example.neocafewaiterapplication.viewModel.new_order.NewOrderProductsViewModel
import com.example.neocafewaiterapplication.viewModel.orders_vm.ReceiptViewModel
import com.example.neocafewaiterapplication.viewModel.orders_vm.TableViewModel
import com.example.neocafewaiterapplication.koin.retrofitModule
import com.example.neocafewaiterapplication.viewModel.notification_vm.NotificationViewModel
import com.example.neocafewaiterapplication.viewModel.registration_vm.RegistrationViewModel
import com.example.neocafewaiterapplication.viewModel.user_vm.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.INFO)
            modules(listOf(myModule, retrofitModule))
        }

    }

    companion object {
        lateinit var instance: MyApplication
        fun getContext(): Context? {
            return instance.applicationContext
        }
    }
}

val myModule = module {
    viewModel { AllProductViewModel(get()) }
    viewModel { NewOrderProductsViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { TableViewModel(get()) }
    viewModel { MenuViewModel() }
    viewModel { ReceiptViewModel(get()) }
    viewModel { NewOrderViewModel(get()) }
    viewModel { NotificationViewModel(get()) }

    single { LocalDatabase(androidContext().applicationContext) }
}