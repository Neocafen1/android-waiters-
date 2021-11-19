package com.example.neocafewaiterapplication.koin

import com.example.neocafewaiterapplication.model.retrofit.Menu_API
import com.example.neocafewaiterapplication.model.retrofit.RegistrationAPI
import com.example.neocafewaiterapplication.model.retrofit.StaffApi
import com.example.neocafewaiterapplication.model.retrofit.ValidateInterceptor
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.Consts
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module{

    factory { getOkHttp() }
    factory { getRetrofitBuilder(okHttpClient = get()) }

    single { getStaffApi(retrofit = get()) }
    single { getMenuApi(retrofit = get()) }
    factory { Repository(menuAPi = get(), registrationAPI = get(), staffApi = get()) }
    single { getRegisterAPI(retrofit = get()) }
}

fun getRegisterAPI(retrofit: Retrofit): RegistrationAPI {
    return retrofit.create(RegistrationAPI::class.java)
}

fun getStaffApi(retrofit: Retrofit):StaffApi{
    return retrofit.create(StaffApi::class.java)
}
fun getMenuApi(retrofit: Retrofit):Menu_API {
    return retrofit.create(Menu_API::class.java)
}

fun getValidateInterceptor():Interceptor{
    return ValidateInterceptor()
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
        .addInterceptor(getValidateInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(Consts.READ_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(Consts.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .build()
}


