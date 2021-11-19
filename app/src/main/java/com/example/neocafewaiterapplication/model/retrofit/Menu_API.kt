package com.example.neocafewaiterapplication.model.retrofit

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import retrofit2.http.GET
import retrofit2.http.Path

interface Menu_API {

    @GET("ncafe/products/county/")
    suspend fun getAllProduct():MutableList<AllModels.Product>

    @GET("ncafe/tables/")
    suspend fun getAllTables():MutableList<AllModels.Table>

    @GET("ncafe/orders/status/{status}/")
    suspend fun getProducts(@Path(value = "status", encoded = true) status:String):AllModels.NeoOrder

    @GET("ncafe/orders/")
    suspend fun getAllOrders():AllModels.NeoOrder

}