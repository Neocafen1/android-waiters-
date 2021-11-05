package com.example.neocafewaiterapplication.repository

import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafeteae1prototype.data.models.SafeApiCall
import com.example.neocafewaiterapplication.data.retrofit.Menu_API
import com.example.neocafewaiterapplication.data.retrofit.RegistrationAPI
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

class Repository(
    private val menuAPi: Menu_API,
    private val registrationAPI: RegistrationAPI
) : SafeApiCall {

    suspend fun getAllProduct(): Resource<MutableList<AllModels.Product>> {
        return safeApiCall { menuAPi.getAllProduct() }
    }

    suspend fun getToken(number: Int, uid: String): Resource<AllModels.Token> {
        return safeApiCall { registrationAPI.getToken(number, uid) }
    }

}