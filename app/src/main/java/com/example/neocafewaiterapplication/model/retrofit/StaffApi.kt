package com.example.neocafewaiterapplication.model.retrofit

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import retrofit2.http.*

interface StaffApi {

    @GET("staff/")
    suspend fun getWaitersData(): AllModels.UserInfo

    @GET("alternative_schedule/")
    suspend fun getSchedule(): List<AllModels.WorkTime>

    @GET("rating/")
    suspend fun getRating(): Int

    @FormUrlEncoded
    @PATCH("userch/")
    suspend fun changeUserNameAndSurname(
        @Field("first_name") name: String,
        @Field("last_name") surname:String
    ):Boolean

}