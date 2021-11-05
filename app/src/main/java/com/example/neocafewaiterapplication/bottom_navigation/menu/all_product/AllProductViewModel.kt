package com.example.neocafewaiterapplication.bottom_navigation.menu.all_product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import com.example.neocafewaiterapplication.tools.sortByCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AllProductViewModel(private val repository: Repository) : ViewModel() {

    val list = MutableLiveData<Resource<MutableList<AllModels.Product>>>()

    init {
        getAllProduct()
    }

    private fun getAllProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = async { repository.getAllProduct() }.await()
            list.postValue(response)
        }
    }


    fun sort(category:String, list:MutableList<AllModels.Product>):MutableList<AllModels.Product>{
        return list.sortByCategory(category)
    }

}