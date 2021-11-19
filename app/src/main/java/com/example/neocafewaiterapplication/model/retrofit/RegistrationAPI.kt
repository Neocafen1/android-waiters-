package com.example.neocafewaiterapplication.model.retrofit

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegistrationAPI {

    @FormUrlEncoded
    @POST("token/")
    suspend fun getToken(
        @Field("number") number: Int,
        @Field("password") uid: String
    ): AllModels.Token

    @FormUrlEncoded
    @POST("refresh/")
    fun updateAccessTokenWithRefresh(@Field("refresh") token: String): Call<AllModels.RefreshResponse>

    @FormUrlEncoded
    @POST
    suspend fun checkWaiterNumber(@Field("number") number:Int): Response<Boolean>

    @POST
    suspend fun saveUserInfo(@Body userInfo: AllModels.UserInfo):Boolean

}