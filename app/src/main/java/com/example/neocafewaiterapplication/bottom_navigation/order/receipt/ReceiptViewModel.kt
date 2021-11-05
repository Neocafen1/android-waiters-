package com.example.neocafewaiterapplication.bottom_navigation.order.receipt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

class ReceiptViewModel : ViewModel() {

    private val list = MutableLiveData<MutableList<AllModels.Order>>()





    init {
        val productList = listOf<AllModels.ProductOfReceipt>(
            AllModels.ProductOfReceipt("Новый", "Тирраммиссу", 100, 8),
            AllModels.ProductOfReceipt("В процессе", "Капучино", 150, 2),
            AllModels.ProductOfReceipt("В процессе", "Круассан", 200, 5),
            AllModels.ProductOfReceipt("Готово", "Латте", 400, 4),
        )

        val secondProductList = listOf<AllModels.ProductOfReceipt>(
            AllModels.ProductOfReceipt("В процессе", "ыврплоыврпып", 100, 8),
            AllModels.ProductOfReceipt("Готово", "ыовпдыождпыпы", 150, 2),
            AllModels.ProductOfReceipt("Новый", "оывпдлыопвп", 200, 5),
            AllModels.ProductOfReceipt("Готово", "ывлдопыдлопдлыоп", 400, 4),
        )

        val tableList = mutableListOf<AllModels.Order>(
            AllModels.Order(1,"16:34", "Готово", 1245, productList, "Алмаз", "15:45"),
        AllModels.Order(2,"16:34", "В процессе", 1245, productList, "Алмаз", "15:45"),
        AllModels.Order(3,"16:34", "Отменено", 1245,productList, "Алмаз", "15:45" ),
        AllModels.Order(4,"16:34", "Готово", 1245,productList, "Алмаз", "15:45"),
        AllModels.Order(5,"16:34", "Завершено", 1245, secondProductList, "Bob", "16:40"),
        AllModels.Order(6,"16:34", "Готово", 1245, secondProductList, "Bob", "16:40"),
        AllModels.Order(7,"16:34", "Новый", 1245, secondProductList, "Bob", "16:40"),
        )

        list.value = tableList
    }

    var sortedList = mutableListOf<AllModels.Order>()

    fun sort(category:String, list:MutableList<AllModels.Order>):MutableList<AllModels.Order>{
        val myList = mutableListOf<AllModels.Order>()
        for (i in list){
            if (i.status == category){
                myList.add(i)
            }

        }
        sortedList = myList

        if(category == "Все"){
            return list
        }
        return sortedList
    }

    fun getList(): MutableLiveData<MutableList<AllModels.Order>> {
        return list
    }

}