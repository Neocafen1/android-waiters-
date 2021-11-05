package com.example.neocafewaiterapplication.tools.sealed_classes

import java.io.Serializable

sealed class AllModels : Serializable {

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

    data class Table (val isFree:Boolean, val table_number:Int):AllModels()

    data class WorkTime (val day:String, val startTime:String, val endTime:String, val work:String):AllModels()

    data class Order (val tabelNumber:Int, val time:String, val status:String, val orderNumber:Int, val list:List<AllModels>,val client:String, val openTime:String):AllModels()

    data class ProductOfReceipt(val status: String, val name: String, val price:Int, val county:Int):AllModels()
}