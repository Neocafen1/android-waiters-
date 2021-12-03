package com.example.neocafewaiterapplication.viewModel.orders_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import kotlinx.coroutines.launch

class TableViewModel(private val repository: Repository) : ViewModel() {

    val tableList = MutableLiveData<MutableList<AllModels.Table>>()
    val statusChanged = MutableLiveData<Boolean?>()

    fun getTableList() {
        viewModelScope.launch {
            repository.getAllTables().let {
                if (it is Resource.Success) tableList.postValue(it.value)
            }
        }
    }

    fun changeStatusToFree(table:String){
        viewModelScope.launch {
            repository.changeStatusToFree(table).let {
                if (it is Resource.Success) statusChanged.postValue(it.value)
            }
        }
    }

}