package com.example.neocafewaiterapplication.model.retrofit

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import retrofit2.http.GET

interface StaffApi {

    @GET("staff/")
    suspend fun getWaitersData():AllModels.UserInfo

    @GET("schedule/")
    suspend fun getSchedule():MutableList<AllModels>

    @GET("rating/")
    suspend fun getRating():Int

}