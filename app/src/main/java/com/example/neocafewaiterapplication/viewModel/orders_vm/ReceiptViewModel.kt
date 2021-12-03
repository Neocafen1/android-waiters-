package com.example.neocafewaiterapplication.viewModel.orders_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import kotlinx.coroutines.launch

class ReceiptViewModel(private val repository: Repository) : ViewModel() {

    val orders = MutableLiveData<AllModels.NeoOrder>()
    val isOrderClosed = MutableLiveData<Boolean?>()

    fun getOrders(status:String){
        viewModelScope.launch {
            if (status == "all") {
                repository.getAllOrders().let {
                    if (it is Resource.Success) {
                        orders.postValue(it.value)
                    }
                }
            }else{
                repository.getOrdersByStatus(status).let {
                    if (it is Resource.Success){
                        orders.postValue(it.value)
                    }
                }
            }
        }
    }

    fun closeOrder(id:Int){
        viewModelScope.launch {
            repository.closeOrder(id).let {
                if(it is Resource.Success) isOrderClosed.postValue(it.value)
            }
        }
    }
}