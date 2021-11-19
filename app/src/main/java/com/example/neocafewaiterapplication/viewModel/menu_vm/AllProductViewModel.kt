package com.example.neocafewaiterapplication.viewModel.menu_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.sortByCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AllProductViewModel(private val repository: Repository) : ViewModel() {

    val list = MutableLiveData<MutableList<AllModels.Product>>()

    init {
        getAllProduct()
    }

    private fun getAllProduct() {
        viewModelScope.launch {
            repository.getAllProduct().let {
                if (it is Resource.Success) list.postValue(it.value)
            }
        }
    }

    fun sort(category:String, list:MutableList<AllModels.Product>):MutableList<AllModels.Product>{
        return list.sortByCategory(category)
    }

}