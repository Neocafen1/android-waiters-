package com.example.neocafewaiterapplication.koin

import com.example.neocafewaiterapplication.data.retrofit.Menu_API
import com.example.neocafewaiterapplication.data.retrofit.RegistrationAPI
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.tools.Consts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module{

    factory { getOkHttp() }
    factory { getRetrofitBuilder(okHttpClient = get()) }
    single { getMenuApi(retrofit = get()) }
    factory { Repository(menuAPi = get(), registrationAPI = get()) }
    single { getRegisterAPI(retrofit = get()) }
}

fun getRegisterAPI(retrofit: Retrofit): RegistrationAPI {
    return retrofit.create(RegistrationAPI::class.java)
}

fun getMenuApi(retrofit: Retrofit):Menu_API {
    return retrofit.create(Menu_API::class.java)
}


fun getRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Consts.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getOkHttp():OkHttpClient{
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}


