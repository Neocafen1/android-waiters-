package com.example.neocafewaiterapplication.data.retrofit

import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import retrofit2.http.GET

interface Menu_API {


    @GET("ncafe/products/county/")
    suspend fun getAllProduct():MutableList<AllModels.Product>

}