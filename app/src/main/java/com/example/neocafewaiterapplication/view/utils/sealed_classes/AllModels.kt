package com.example.neocafewaiterapplication.view.utils.sealed_classes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

sealed class AllModels : Serializable {

    data class NeoOrder(
        val orders: MutableList<Order>
    )

    data class  Order(
        val bonus: Int,
        val total_sum: Int,
        val date: String,
        val time: String,
        val id: Int,
        val orderItems: List<ProductOfReceipt>,
        val status: String,
        val tableId: Int,
        val userId: Int,
        val username: String
    ):AllModels()

    data class FinishProduct(val order: FinishOrder,
                             val orderItems: MutableList<OrderItem>)

    data class FinishOrder(val tableId:Int)

    data class OrderItem(
        val productId: Int,
        val quantity: Int
    )

    data class ProductOfReceipt(
        val productId: Int,
        val productTitle: String,
        val price:Int,
        val quantity: Int,
        val sum:Int
    ):AllModels()

    data class RefreshResponse(@SerializedName("access")
                               val access: String
    )

    data class Menu(val icon:Int, val category:String): AllModels()

    data class Token(val access:String, val refresh:String)

    data class Product(  val id:Int,
                         val category_name: String,
                         val description: String,
                         val image: String,
                         val isPopular: Boolean,
                         val price: Int,
                         val title: String,
                         var county: Int): AllModels()

    data class Notification (val status:String, val products:String, val time:String): AllModels()

    data class Table (val id:Int, val qrCode:String, val user:String?):AllModels()

    data class UserInfo(val number:Int, val first_name:String, val last_name:String, val birthDate:String?, val password:String)

    data class WorkTime (val day:String, val work_time:String)
}