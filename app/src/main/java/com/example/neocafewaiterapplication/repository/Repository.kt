package com.example.neocafewaiterapplication.repository

import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafeteae1prototype.data.models.SafeApiCall
import com.example.neocafewaiterapplication.model.retrofit.Menu_API
import com.example.neocafewaiterapplication.model.retrofit.RegistrationAPI
import com.example.neocafewaiterapplication.model.retrofit.StaffApi
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import retrofit2.Response

class Repository(
    private val menuAPi: Menu_API,
    private val registrationAPI: RegistrationAPI,
    private val staffApi: StaffApi
) : SafeApiCall {

    suspend fun getAllProduct(): Resource<MutableList<AllModels.Product>> {
        return safeApiCall { menuAPi.getAllProduct() }
    }

    suspend fun getSchedule():Resource<List<AllModels.WorkTime>>{
        return safeApiCall { staffApi.getSchedule() }
    }

    suspend fun checkNumber(number: Int):Response<Boolean>{
        return registrationAPI.checkWaiterNumber(number)
    }

    suspend fun getToken(number: Int, uid: String): Resource<AllModels.Token> {
        return safeApiCall { registrationAPI.getToken(number, uid) }
    }

    suspend fun getUserInfo():Resource<AllModels.UserInfo>{
        return safeApiCall { staffApi.getWaitersData() }
    }

    suspend fun saveUserInfo(userInfo:AllModels.UserInfo):Resource<Boolean>{
        return safeApiCall { registrationAPI.saveUserInfo(userInfo)}
    }

    suspend fun getAllTables():Resource<MutableList<AllModels.Table>>{
        return safeApiCall { menuAPi.getAllTables() }
    }

    suspend fun getOrdersByStatus(status:String):Resource<AllModels.NeoOrder>{
        return safeApiCall { menuAPi.getProducts(status) }
    }

    suspend fun getAllOrders():Resource<AllModels.NeoOrder>{
        return safeApiCall { menuAPi.getAllOrders() }
    }

    suspend fun getRating():Resource<Int>{
        return safeApiCall { staffApi.getRating() }
    }

    suspend fun changeNameAndSurname(name:String, surname:String):Resource<Boolean>{
        return safeApiCall { staffApi.changeUserNameAndSurname(name,surname) }
    }

    suspend fun createOrder(order:AllModels.FinishProduct):Resource<Boolean>{
        return safeApiCall { menuAPi.createOrder(order) }
    }

    suspend fun changeStatusToFree(table:String):Resource<Boolean>{
        return safeApiCall { menuAPi.changeStatusToFree(table) }
    }

    suspend fun closeOrder(id:Int):Resource<Boolean>{
        return safeApiCall { menuAPi.closeOrder(id) }
    }

}