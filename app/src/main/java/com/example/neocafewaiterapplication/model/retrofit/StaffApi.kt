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

    @POST("ncafe/fcm/create/")
    suspend fun postFCMToken(@Body model:AllModels.FCM_token):Boolean

    @GET("ncafe/notifications/?type=W")
    suspend fun getNotifications():MutableList<AllModels.Notification>

    @DELETE("ncafe/notifications/?type=W")
    suspend fun deleteAllNotifications():Boolean

    @DELETE("ncafe/notifications/")
    suspend fun deleteUserNotifications(@Query("type") type:String, @Query("id")id:Int):Boolean

    @POST("ncafe/check-user/")
    suspend fun checkUser(@Body model:AllModels.UserData):Boolean
}