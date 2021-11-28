package com.example.neocafewaiterapplication.model.retrofit

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import retrofit2.http.*

interface Menu_API {

    @GET("ncafe/products/county/")
    suspend fun getAllProduct():MutableList<AllModels.Product>

    @GET("ncafe/tables/")
    suspend fun getAllTables():MutableList<AllModels.Table>

    @GET("ncafe/orders/status/{status}/")
    suspend fun getProducts(@Path(value = "status", encoded = true) status:String):AllModels.NeoOrder

    @GET("ncafe/orders/")
    suspend fun getAllOrders():AllModels.NeoOrder

    @POST("ncafe/orders/")
    suspend fun createOrder(@Body order:AllModels.FinishProduct):Boolean

    @DELETE("ncafe/tables/{table}/")
    suspend fun changeStatusToFree(@Path(value = "table",encoded = true) table:String):Boolean

    @FormUrlEncoded
    @POST("ncafe/orders/close/")
    suspend fun closeOrder(@Field("order")id:Int):Boolean

}