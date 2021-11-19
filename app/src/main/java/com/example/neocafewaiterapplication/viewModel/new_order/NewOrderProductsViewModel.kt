package com.example.neocafewaiterapplication.viewModel.new_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.getTotalPrice
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.sortByCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewOrderProductsViewModel(private val repository: Repository) : ViewModel() {

    var totalPrice = 0
    var sortedList = mutableListOf<AllModels.Product>()
    val finishList = mutableListOf<AllModels.Product>() // list для итогового чека
    val productLiveData = MutableLiveData<MutableList<AllModels.Product>>()

    init {getAllProduct()}

    private fun getAllProduct() {
        CoroutineScope(Dispatchers.IO).launch {
           repository.getAllProduct().let {
               if (it is Resource.Success){
                   productLiveData.postValue(it.value)
               }
           }
        }
    }

    fun getProductsTotalPrice():Int{ // В начале получаем итоговую цену
        totalPrice = 0
        viewModelScope.launch {
            totalPrice = productLiveData.value?.getTotalPrice() ?: 0
        }
        return totalPrice
    }


    fun sort(category:String, list:MutableList<AllModels.Product>){ // Сортирует по категориям
        sortedList = list.sortByCategory(category)
    }

    fun createFinishList(list:MutableList<AllModels.Product>): MutableList<AllModels.Product> { // Показывает лист выбраныых продуктов
        finishList.clear()
        list.forEach {
            if (it.county > 0){
                finishList.add(it)
            }
        }
        return finishList
    }

    fun totalPriceAfterQuanityChange(list:MutableList<AllModels.Product>, method: String, name:String){ // Итоговая цена
       list.forEach{i ->
            if (i.title == name && method == "+"){
                totalPrice += i.price
            }
            else if (i.title == name && method == "-"){
                totalPrice -= i.price
            }
        }
    }

    fun discard(position: Int) {
        val model = finishList[position]
        productLiveData.value!!.forEach { i->
            if (i.title == model.title){
                i.county = 0
            }
        }
        finishList.removeAt(position)
    }
}