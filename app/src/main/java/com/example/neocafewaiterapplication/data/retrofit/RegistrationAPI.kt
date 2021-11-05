package com.example.neocafewaiterapplication.data.retrofit

import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationAPI {

    @FormUrlEncoded
    @POST("token/")
    suspend fun getToken(@Field("number")number:Int,@Field("password") uid:String):AllModels.Token



}